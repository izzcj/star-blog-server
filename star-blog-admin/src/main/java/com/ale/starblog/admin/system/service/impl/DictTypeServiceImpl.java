package com.ale.starblog.admin.system.service.impl;

import com.ale.starblog.admin.common.constants.TranslationConstants;
import com.ale.starblog.admin.system.domain.entity.DictType;
import com.ale.starblog.admin.system.domain.pojo.dict.type.CreateDictTypeDTO;
import com.ale.starblog.admin.system.domain.pojo.dict.type.DictTypeBO;
import com.ale.starblog.admin.system.domain.pojo.dict.type.ModifyDictTypeDTO;
import com.ale.starblog.admin.system.mapper.DictTypeMapper;
import com.ale.starblog.admin.system.service.IDictTypeService;
import com.ale.starblog.framework.core.service.BaseServiceImpl;
import com.ale.starblog.framework.core.translation.GenericTranslationSupport;
import org.springframework.stereotype.Service;

/**
 * 字典类型服务实现类
 *
 * @author Ale
 * @version 1.0.0 2025/5/29 11:11
 */
@Service
public class DictTypeServiceImpl extends BaseServiceImpl<DictTypeMapper, DictType, DictTypeBO, CreateDictTypeDTO, ModifyDictTypeDTO> implements IDictTypeService {

    @Override
    public void refreshCache() {
        GenericTranslationSupport.publishUpdateEvent(TranslationConstants.TRANSLATION_DICT);

    }
}
