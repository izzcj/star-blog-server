package com.ale.starblog.admin.system.service.impl;

import com.ale.starblog.admin.common.constants.TranslationConstants;
import com.ale.starblog.admin.system.domain.entity.DictData;
import com.ale.starblog.admin.system.domain.entity.DictType;
import com.ale.starblog.admin.system.domain.pojo.dict.type.DictTypeBO;
import com.ale.starblog.admin.system.mapper.DictTypeMapper;
import com.ale.starblog.admin.system.service.IDictDataService;
import com.ale.starblog.admin.system.service.IDictTypeService;
import com.ale.starblog.framework.core.service.AbstractCrudServiceImpl;
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
public class DictTypeServiceImpl extends AbstractCrudServiceImpl<DictTypeMapper, DictType, DictTypeBO> implements IDictTypeService {

    /**
     * 字典数据服务
     */
    private final IDictDataService dictDataService;

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

    @Override
    public void refreshCache() {
        GenericTranslationSupport.publishUpdateEvent(TranslationConstants.TRANSLATION_DICT);
    }
}
