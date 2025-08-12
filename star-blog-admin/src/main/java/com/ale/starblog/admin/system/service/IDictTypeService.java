package com.ale.starblog.admin.system.service;

import com.ale.starblog.admin.system.domain.entity.DictType;
import com.ale.starblog.admin.system.domain.pojo.dict.type.CreateDictTypeDTO;
import com.ale.starblog.admin.system.domain.pojo.dict.type.DictTypeBO;
import com.ale.starblog.admin.system.domain.pojo.dict.type.ModifyDictTypeDTO;
import com.ale.starblog.framework.core.service.IBaseService;

/**
 * 字典类型服务接口
 *
 * @author Ale
 * @version 1.0.0 2025/5/29 11:10
 */
public interface IDictTypeService extends IBaseService<DictType, DictTypeBO, CreateDictTypeDTO, ModifyDictTypeDTO> {

    /**
     * 刷新缓存
     */
    void refreshCache();

}
