package com.ale.starblog.admin.common.translation;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.ale.starblog.admin.common.constants.TranslationConstants;
import com.ale.starblog.admin.system.domain.entity.DictData;
import com.ale.starblog.admin.system.service.DictDataService;
import com.ale.starblog.framework.common.utils.CastUtils;
import com.ale.starblog.framework.core.translation.GenericTranslationMappingData;
import com.ale.starblog.framework.core.translation.GenericTranslationMappingDataLoader;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典翻译数据加载器
 *
 * @author Ale
 * @version 1.0.0 2025/5/30 15:03
 */
@Component
@RequiredArgsConstructor
public class DictTranslationMappingDataLoader implements GenericTranslationMappingDataLoader {

    /**
     * 字典数据服务
     */
    private final DictDataService dictDataService;

    @Override
    public boolean supports(String s) {
        return TranslationConstants.TRANSLATION_DICT.equals(s);
    }

    @Override
    public Map<String, String> load(String type, Map<String, Object> params) {
        if (CollectionUtil.isEmpty(params)) {
            return Collections.emptyMap();
        }
        String dictType = CastUtils.cast(params.get(TranslationConstants.TRANSLATION_DICT_KEY));
        if (StrUtil.isBlank(dictType)) {
            return Collections.emptyMap();
        }
        List<DictData> dictDataList = this.dictDataService.lambdaQuery()
            .eq(DictData::getDictKey, dictType)
            .list();
        return dictDataList.stream()
            .collect(Collectors.toMap(DictData::getDictValue, DictData::getDictLabel));
    }

    @Override
    public List<GenericTranslationMappingData> loadAll() {
        List<DictData> allDictData = this.dictDataService.list();
        if (CollectionUtil.isEmpty(allDictData)) {
            return Collections.emptyList();
        }
        Map<String, List<DictData>> dictDataGroupMap = allDictData.stream()
            .collect(Collectors.groupingBy(DictData::getDictKey));
        List<GenericTranslationMappingData> result = Lists.newArrayListWithCapacity(dictDataGroupMap.size());
        dictDataGroupMap.forEach((dictKey, dictDataList) -> result.add(
            GenericTranslationMappingData.builder()
                .type(TranslationConstants.TRANSLATION_DICT)
                .params(Collections.singletonMap(TranslationConstants.TRANSLATION_DICT_KEY, dictKey))
                .mappingData(
                    dictDataList.stream()
                        .collect(Collectors.toMap(DictData::getDictValue, DictData::getDictLabel))
                )
                .build()
        ));
        return result;
    }
}
