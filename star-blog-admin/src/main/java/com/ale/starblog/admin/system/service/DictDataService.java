package com.ale.starblog.admin.system.service;

import com.ale.starblog.admin.system.domain.entity.DictData;
import com.ale.starblog.admin.system.domain.pojo.dict.data.DictDataBO;
import com.ale.starblog.admin.system.domain.pojo.dict.data.DictDataQuery;
import com.ale.starblog.admin.system.mapper.DictDataMapper;
import com.ale.starblog.framework.core.service.AbstractCrudService;
import org.springframework.stereotype.Service;

/**
 * 字典数据服务实现类
 *
 * @author Ale
 * @version 1.0.0 2025/5/30 14:42
 */
@Service
public class DictDataService extends AbstractCrudService<DictDataMapper, DictData, DictDataBO, DictDataQuery> {
}
