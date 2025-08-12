package com.ale.starblog.framework.workflow.dao.mybatis;

import com.ale.starblog.framework.workflow.dao.FlowInstanceDao;
import com.ale.starblog.framework.workflow.entity.FlowInstance;
import com.ale.starblog.framework.workflow.dao.mybatis.mapper.FlowInstanceMapper;
import com.ale.starblog.framework.workflow.query.ActiveInstanceQuery;
import com.ale.starblog.framework.workflow.query.mybatis.MybatisPlusActiveInstanceQuery;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * 基于MybatisPlus的FlowInstanceDao实现
 *
 * @author Ale
 * @version 1.0.0 2025/6/26 17:12
 */
public class MybatisPlusFlowInstanceDao implements FlowInstanceDao {

    /**
     * 流程实例Mapper
     */
    private final FlowInstanceMapper instanceMapper;

    public MybatisPlusFlowInstanceDao(FlowInstanceMapper instanceMapper) {
        this.instanceMapper = instanceMapper;
    }

    @Override
    public ActiveInstanceQuery createActiveInstanceQuery() {
        return new MybatisPlusActiveInstanceQuery(this.instanceMapper);
    }

    @Override
    public boolean insert(FlowInstance flowInstance) {
        return this.instanceMapper.insert(flowInstance) > 0;
    }

    @Override
    public FlowInstance selectById(String instanceId) {
        return this.instanceMapper.selectById(instanceId);
    }

    @Override
    public FlowInstance selectByBusinessIdAndType(String businessId, String businessType) {
        return this.instanceMapper.selectOne(
            Wrappers.<FlowInstance>lambdaQuery()
                .eq(FlowInstance::getBusinessId, businessId)
                .eq(FlowInstance::getBusinessType, businessType)
        );
    }

    @Override
    public boolean updateById(FlowInstance flowInstance) {
        return this.instanceMapper.updateById(flowInstance) > 0;
    }

    @Override
    public boolean deleteById(String instanceId) {
        return this.instanceMapper.deleteById(instanceId) > 0;
    }
}
