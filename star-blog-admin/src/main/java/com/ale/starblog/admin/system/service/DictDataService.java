package com.ale.starblog.admin.system.service;

import cn.hutool.core.lang.Pair;
import com.ale.starblog.admin.common.constants.TranslationConstants;
import com.ale.starblog.admin.system.domain.entity.DictData;
import com.ale.starblog.admin.system.domain.pojo.dict.data.DictDataBO;
import com.ale.starblog.admin.system.domain.pojo.dict.data.DictDataQuery;
import com.ale.starblog.admin.system.mapper.DictDataMapper;
import com.ale.starblog.framework.common.support.PatchData;
import com.ale.starblog.framework.core.service.AbstractCrudService;
import com.ale.starblog.framework.core.service.hook.HookContext;
import com.ale.starblog.framework.core.translation.GenericTranslationSupport;
import com.ale.starblog.framework.core.translation.TranslationPatchType;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 字典数据服务实现类
 *
 * @author Ale
 * @version 1.0.0 2025/5/30 14:42
 */
@Service
public class DictDataService extends AbstractCrudService<DictDataMapper, DictData, DictDataBO, DictDataQuery> {

    @Override
    public void afterCreate(DictData entity, HookContext context) {
        this.publishDictTranslationPatch(entity, TranslationPatchType.ADD);
    }

    @Override
    public void afterModify(DictData entity, HookContext context) {
        // 即使修改了dictValue，也不移除旧值
        this.publishDictTranslationPatch(entity, TranslationPatchType.CHANGE);
    }

    @Override
    public void afterDelete(DictData entity, HookContext context) {
        this.publishDictTranslationPatch(entity, TranslationPatchType.REMOVE);
    }

    /**
     * 发布字典数据翻译补丁事件
     *
     * @param entity 字典数据实体
     * @param patchType 补丁类型
     */
    private void publishDictTranslationPatch(DictData entity, TranslationPatchType patchType) {
        Map<String, Object> params = this.buildParams(entity);
        Pair<String, String> pair = Pair.of(entity.getDictValue(), entity.getDictLabel());

        PatchData<Pair<String, String>> patchData = GenericTranslationSupport.buildPatchData(pair, patchType);

        GenericTranslationSupport.publishUpdateEvent(
            TranslationConstants.TRANSLATION_DICT,
            params,
            patchData
        );
    }

    /**
     * 构建翻译参数
     *
     * @param entity 字典数据实体
     * @return 翻译参数
     */
    private Map<String, Object> buildParams(DictData entity) {
        return Map.of(TranslationConstants.TRANSLATION_DICT_KEY, entity.getDictKey());
    }
}
