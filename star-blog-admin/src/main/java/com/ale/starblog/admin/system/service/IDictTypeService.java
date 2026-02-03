package com.ale.starblog.admin.system.service;

import com.ale.starblog.admin.system.domain.entity.DictType;
import com.ale.starblog.admin.system.domain.pojo.dict.type.DictTypeBO;
import com.ale.starblog.admin.system.domain.pojo.dict.type.DictTypeQuery;
import com.ale.starblog.framework.core.service.ICrudService;

/**
 * 字典类型服务接口
 *
 * @author Ale
 * @version 1.0.0 2025/5/29 11:10
 */
public interface IDictTypeService extends ICrudService<DictType, DictTypeBO, DictTypeQuery> {

    /**
     * 刷新缓存
     */
    void refreshCache();

}
