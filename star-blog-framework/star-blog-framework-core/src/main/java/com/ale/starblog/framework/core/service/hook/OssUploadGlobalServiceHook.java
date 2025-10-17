package com.ale.starblog.framework.core.service.hook;

import cn.hutool.core.collection.CollectionUtil;
import com.ale.starblog.framework.common.domain.entity.BaseEntity;
import com.ale.starblog.framework.common.support.ReflectionField;
import com.ale.starblog.framework.common.utils.ReflectionUtils;
import com.ale.starblog.framework.core.constants.HookConstants;
import com.ale.starblog.framework.core.oss.OssServiceProvider;
import com.ale.starblog.framework.core.oss.OssSupport;
import com.ale.starblog.framework.core.oss.OssUpload;
import com.ale.starblog.framework.core.oss.support.OssUploadKeyReplacer;
import com.ale.starblog.framework.core.oss.support.OssUploadKeyResolver;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 对象上传全局钩子
 *
 * @author Ale
 * @version 1.0.0 2025/10/16 15:06
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OssUploadGlobalServiceHook implements GlobalServiceHook<BaseEntity> {

    /**
     * OSS上传字段解析器
     */
    private final ObjectProvider<OssUploadKeyResolver> ossUploadKeyResolvers;

    /**
     * OSS上传字段替换器
     */
    private final ObjectProvider<OssUploadKeyReplacer> ossUploadKeyReplacers;

    /**
     * 转换服务
     */
    private final ConversionService conversionService;

    @Override
    public void beforeSave(BaseEntity entity, HookContext context) {
        List<ReflectionField> reflectionFields = ReflectionUtils.getClassAnnotatedFields(entity.getClass(), OssUpload.class);
        if (reflectionFields.isEmpty()) {
            return;
        }

        Map<OssServiceProvider, Set<String>> movedObjectKeys = Maps.newHashMapWithExpectedSize(reflectionFields.size());
        Map<OssServiceProvider, Set<String>> willBeRemovedObjectKeys = Maps.newHashMapWithExpectedSize(reflectionFields.size());
        this.handleOssUploadFields(entity, context.get(HookConstants.OLD_ENTITY_KEY), reflectionFields, movedObjectKeys, willBeRemovedObjectKeys);

        TransactionSynchronizationManager.registerSynchronization(
            new UploadedObjectCleanupTransactionSynchronization(
                movedObjectKeys,
                willBeRemovedObjectKeys
            )
        );
    }

    @Override
    public void beforeBatchSave(List<BaseEntity> entityList, HookContext context) {
        BaseEntity firstEntity = entityList.getFirst();
        List<ReflectionField> ossUploadFields = ReflectionUtils.getClassAnnotatedFields(firstEntity.getClass(), OssUpload.class);
        if (CollectionUtil.isEmpty(ossUploadFields)) {
            return;
        }

        Map<OssServiceProvider, Set<String>> allMovedObjectKeys = Maps.newHashMapWithExpectedSize(ossUploadFields.size() * entityList.size());
        Map<OssServiceProvider, Set<String>> allWillBeRemovedObjectKeys = Maps.newHashMapWithExpectedSize(ossUploadFields.size() * entityList.size());
        Map<Long, BaseEntity> oldEntityMap = context.getOrDefault(HookConstants.OLD_ENTITY_MAP_KEY, Collections.emptyMap());
        for (BaseEntity entity : entityList) {
            Map<OssServiceProvider, Set<String>> movedObjectKeys = Maps.newHashMapWithExpectedSize(ossUploadFields.size());
            Map<OssServiceProvider, Set<String>> willBeRemovedObjectKeys = Maps.newHashMapWithExpectedSize(ossUploadFields.size());
            this.handleOssUploadFields(entity, oldEntityMap.get(entity.getId()), ossUploadFields, movedObjectKeys, willBeRemovedObjectKeys);
            allMovedObjectKeys.putAll(movedObjectKeys);
            allWillBeRemovedObjectKeys.putAll(willBeRemovedObjectKeys);
        }

        TransactionSynchronizationManager.registerSynchronization(
            new UploadedObjectCleanupTransactionSynchronization(allMovedObjectKeys, allWillBeRemovedObjectKeys)
        );
    }

    @Override
    public void afterDelete(BaseEntity entity, HookContext context) {
        this.removeUploadedFile(entity);
    }

    @Override
    public void afterBatchDelete(List<BaseEntity> entityList, HookContext context) {
        for (BaseEntity entity : entityList) {
            this.removeUploadedFile(entity);
        }
    }

    /**
     * 处理上传字段
     *
     * @param entity                  实体
     * @param oldEntity               旧实体
     * @param reflectionFields        反射字段列表
     * @param movedObjectKeys         移动对象key集合
     * @param willBeRemovedObjectKeys 将被移除的对象key集合
     */
    private void handleOssUploadFields(BaseEntity entity, BaseEntity oldEntity, List<ReflectionField> reflectionFields, Map<OssServiceProvider, Set<String>> movedObjectKeys, Map<OssServiceProvider, Set<String>> willBeRemovedObjectKeys) {
        for (ReflectionField reflectionField : reflectionFields) {
            OssUpload ossUpload = reflectionField.field().getAnnotation(OssUpload.class);
            OssServiceProvider ossProvider = ossUpload.provider();
            Collection<String> oldObjectKeys = null;
            if (oldEntity != null) {
                oldObjectKeys = this.resolveKeys(reflectionField.field(), reflectionField.getValue(oldEntity));
            }
            Collection<String> newObjectKeys = this.resolveKeys(reflectionField.field(), reflectionField.getValue(entity));

            if (CollectionUtil.isEmpty(oldObjectKeys) && CollectionUtil.isEmpty(newObjectKeys)) {
                return;
            }

            // 旧字段为空，新字段不为空，需要移动新对象
            if (CollectionUtil.isEmpty(oldObjectKeys)) {
                List<String> movedKeys = moveNewObjectKeys(ossProvider, newObjectKeys, movedObjectKeys);
                this.setReplacedValue(reflectionField, entity, reflectionField.getValue(entity), newObjectKeys, movedKeys);
            } else if (CollectionUtil.isEmpty(newObjectKeys)) {
                // 旧字段有值，新字段为空，需要移除旧对象
                Set<String> removedObjectKeys = willBeRemovedObjectKeys
                    .computeIfAbsent(
                        ossProvider,
                        k -> Sets.newHashSetWithExpectedSize(reflectionFields.size())
                    );
                removedObjectKeys.addAll(oldObjectKeys);
                this.setReplacedValue(reflectionField, entity, reflectionField.getValue(entity), Collections.emptyList(), Collections.emptyList());
            } else {
                // 同时有值则做比较，移动新增的，移除被删除的
                Set<String> oldObjectKeySet = Sets.newHashSet(oldObjectKeys);
                Set<String> newObjectKeysSet = Sets.newHashSet(newObjectKeys);

                // 取差集
                Collection<String> disjunction = CollectionUtil.disjunction(newObjectKeysSet, oldObjectKeySet);
                Set<String> keys = movedObjectKeys
                    .computeIfAbsent(
                        ossProvider,
                        k -> Sets.newHashSetWithExpectedSize(reflectionFields.size())
                    );
                Set<String> removedObjectKeys = willBeRemovedObjectKeys
                    .computeIfAbsent(
                        ossProvider,
                        k -> Sets.newHashSetWithExpectedSize(reflectionFields.size())
                    );

                for (String key : disjunction) {
                    if (newObjectKeysSet.contains(key)) {
                        // 新增的key
                        String movedObjectKey = OssSupport.moveObject(ossProvider, key);
                        keys.add(movedObjectKey);
                        oldObjectKeySet.add(movedObjectKey);
                    } else if (oldObjectKeySet.contains(key)) {
                        // 删除的key
                        oldObjectKeySet.remove(key);
                        removedObjectKeys.add(key);
                    }
                }

                this.setReplacedValue(reflectionField, entity, reflectionField.getValue(entity), newObjectKeys, oldObjectKeySet);
            }
        }
    }

    /**
     * 移动新对象文件
     *
     * @param provider        OSS服务提供器
     * @param newObjectKeys   新对象key集合
     * @param movedObjectKeys 移动对象key集合
     * @return 已移动的对象key集合
     */
    private List<String> moveNewObjectKeys(OssServiceProvider provider, Collection<String> newObjectKeys, Map<OssServiceProvider, Set<String>> movedObjectKeys) {
        Set<String> keys = movedObjectKeys.computeIfAbsent(provider, k -> Sets.newHashSetWithExpectedSize(newObjectKeys.size()));
        List<String> movedKeys = Lists.newArrayListWithExpectedSize(newObjectKeys.size());
        for (String newObjectKey : newObjectKeys) {
            String movedObjectKey = OssSupport.moveObject(provider, newObjectKey);
            keys.add(movedObjectKey);
            movedKeys.add(movedObjectKey);
        }
        return movedKeys;
    }

    /**
     * 事务回滚时清理已经上传的对象文件，事务提交时删除不需要的对象文件
     * 此逻辑使用@TransactionalEventListener也可以实现，但是会对外暴露Event和EventListener
     *
     * @param movedObjectKeys         已移动的对象Key
     * @param willBeRemovedObjectKeys 将要删除的对象Key
     */
    private record UploadedObjectCleanupTransactionSynchronization(Map<OssServiceProvider, Set<String>> movedObjectKeys,
                                                                   Map<OssServiceProvider, Set<String>> willBeRemovedObjectKeys) implements TransactionSynchronization {
        /**
         * 删除对象文件
         *
         * @param ossServiceProvider OSS实现
         * @param objectKeys         对象Key集合
         */
        private void removeObjects(OssServiceProvider ossServiceProvider, Set<String> objectKeys) {
            OssSupport.removeObjects(ossServiceProvider, objectKeys);
        }

        /**
         * 移动对象文件到临时目录
         *
         * @param ossServiceProvider OSS实现
         * @param objectKeys         对象Key集合
         */
        private void moveObjectsToTemporaryDirectory(OssServiceProvider ossServiceProvider, Set<String> objectKeys) {
            for (String objectKey : objectKeys) {
                OssSupport.backObject(ossServiceProvider, objectKey);
            }
        }

        @Override
        public int getOrder() {
            return HIGHEST_PRECEDENCE;
        }

        @Override
        public void afterCommit() {
            this.willBeRemovedObjectKeys.forEach(this::removeObjects);
        }

        @Override
        public void afterCompletion(int status) {
            if (status == STATUS_COMMITTED) {
                return;
            }

            // 其他情况均认为失败，移动文件到原处（临时目录）
            this.movedObjectKeys.forEach(this::moveObjectsToTemporaryDirectory);
        }
    }

    /**
     * 解析上传对象Key
     *
     * @param field 字段
     * @param value 值
     * @return 对象Key集合
     */
    private Collection<String> resolveKeys(Field field, Object value) {
        for (OssUploadKeyResolver ossUploadKeyResolver : this.ossUploadKeyResolvers) {
            var keys = ossUploadKeyResolver.resolveKeys(field, value);
            if (keys != null && !keys.isEmpty()) {
                return keys;
            }
        }

        return Collections.emptyList();
    }

    /**
     * 替换上传对象Key
     *
     * @param reflectionField 反射字段
     * @param entity          实体对象
     * @param value           值
     * @param originalKeys    原始Key集合
     * @param processedKeys   已处理Key集合
     */
    private void setReplacedValue(ReflectionField reflectionField, BaseEntity entity, Object value, Collection<String> originalKeys, Collection<String> processedKeys) {
        for (OssUploadKeyReplacer ossUploadKeyReplacer : this.ossUploadKeyReplacers) {
            var newValue = ossUploadKeyReplacer.replaceKey(reflectionField.field(), value, originalKeys, processedKeys);
            if (newValue != null) {
                reflectionField.setValue(entity, this.conversionService.convert(newValue, reflectionField.field().getType()));
                return;
            }
        }

        log.warn("发现无法处理的上传文件，实体字段：{}.{}，原始值：{}，原始解析Keys：{}，处理后Keys：{}", entity.getClass().getSimpleName(), reflectionField.field().getName(), value, originalKeys, processedKeys);
    }

    /**
     * 删除已上传的文件
     *
     * @param entity 实体
     */
    private void removeUploadedFile(BaseEntity entity) {
        List<ReflectionField> ossUploadFields = ReflectionUtils.getClassAnnotatedFields(entity.getClass(), OssUpload.class);
        if (CollectionUtil.isEmpty(ossUploadFields)) {
            return;
        }
        for (ReflectionField field : ossUploadFields) {
            OssUpload ossUpload = field.field().getAnnotation(OssUpload.class);
            Object fieldValue = field.getValue(entity);
            switch (fieldValue) {
                case String stringValue -> OssSupport.removeObject(ossUpload.provider(), stringValue);
                case List<?> listValue -> {
                    for (Object object : listValue) {
                        if (object instanceof String stringValue) {
                            OssSupport.removeObject(ossUpload.provider(), stringValue);
                        }
                    }
                }
                case null, default -> {
                }
            }
        }
    }
}
