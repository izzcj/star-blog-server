package com.ale.starblog.admin.system.service;

import com.ale.starblog.admin.common.constants.TranslationConstants;
import com.ale.starblog.admin.system.domain.entity.DictData;
import com.ale.starblog.admin.system.domain.entity.DictType;
import com.ale.starblog.admin.system.domain.pojo.dict.type.DictTypeBO;
import com.ale.starblog.admin.system.domain.pojo.dict.type.DictTypeQuery;
import com.ale.starblog.admin.system.mapper.DictTypeMapper;
import com.ale.starblog.framework.core.service.AbstractCrudService;
import com.ale.starblog.framework.core.service.hook.HookContext;
import com.ale.starblog.framework.core.translation.GenericTranslationSupport;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 字典类型服务实现类
 *
 * @author Ale
 * @version 1.0.0 2025/5/29 11:11
 */
@Service
@RequiredArgsConstructor
public class DictTypeService extends AbstractCrudService<DictTypeMapper, DictType, DictTypeBO, DictTypeQuery> {

    /**
     * 字典数据服务
     */
    private final DictDataService dictDataService;

    @Override
    public void afterDelete(DictType entity, HookContext context) {
        this.dictDataService.remove(
            Wrappers.<DictData>lambdaQuery()
                .eq(DictData::getDictKey, entity.getDictKey())
        );
    }

    @Override
    public void afterBatchDelete(List<DictType> entityList, HookContext context) {
        Set<String> dictKeySets = entityList.stream()
            .map(DictType::getDictKey)
            .collect(Collectors.toSet());
        this.dictDataService.remove(
            Wrappers.<DictData>lambdaQuery()
                .in(DictData::getDictKey, dictKeySets)
        );
    }

    /**
     * 刷新缓存
     */
    public void refreshCache() {
        GenericTranslationSupport.publishUpdateEvent(TranslationConstants.TRANSLATION_DICT);
    }
}
