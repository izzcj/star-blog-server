package com.ale.starblog.admin.system.service.impl;

import com.ale.starblog.admin.system.domain.entity.DictData;
import com.ale.starblog.admin.system.domain.pojo.dict.data.CreateDictDataDTO;
import com.ale.starblog.admin.system.domain.pojo.dict.data.DictDataBO;
import com.ale.starblog.admin.system.domain.pojo.dict.data.ModifyDictDataDTO;
import com.ale.starblog.admin.system.mapper.DictDataMapper;
import com.ale.starblog.admin.system.service.IDictDataService;
import com.ale.starblog.framework.core.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 字典数据服务实现类
 *
 * @author Ale
 * @version 1.0.0 2025/5/30 14:42
 */
@Service
public class DictDataServiceImpl extends BaseServiceImpl<DictDataMapper, DictData, DictDataBO, CreateDictDataDTO, ModifyDictDataDTO> implements IDictDataService {
}
