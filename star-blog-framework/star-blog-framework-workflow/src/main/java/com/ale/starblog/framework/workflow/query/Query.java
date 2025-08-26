package com.ale.starblog.framework.workflow.query;

import com.ale.starblog.framework.workflow.entity.FlowEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 查询接口
 *
 * @param <T> 查询结果类型
 * @author Ale
 * @version 1.0.0 2025/7/15 15:10
 */
public interface Query<T extends FlowEntity> {

    /**
     * 查询单条数据
     *
     * @return 结果
     */
    T single();

    /**
     * 查询列表数据
     *
     * @return 结果
     */
    List<T> list();

    /**
     * 查询分页数据
     *
     * @param pageable 分页参数
     * @return 结果
     */
    Page<T> page(Pageable pageable);
}
