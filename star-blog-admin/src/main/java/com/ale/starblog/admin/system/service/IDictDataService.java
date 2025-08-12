package com.ale.starblog.admin.system.service;

import com.ale.starblog.admin.system.domain.entity.DictData;
import com.ale.starblog.admin.system.domain.pojo.dict.data.CreateDictDataDTO;
import com.ale.starblog.admin.system.domain.pojo.dict.data.DictDataBO;
import com.ale.starblog.admin.system.domain.pojo.dict.data.ModifyDictDataDTO;
import com.ale.starblog.framework.core.service.IBaseService;

/**
 * 字典数据服务接口
 *
 * @author Ale
 * @version 1.0.0 2025/5/30 14:40
 */
public interface IDictDataService extends IBaseService<DictData, DictDataBO, CreateDictDataDTO, ModifyDictDataDTO> {
}
