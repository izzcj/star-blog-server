package com.ale.starblog.framework.workflow.support;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * 基于json的变量访问器
 *
 * @author Ale
 * @version 1.0.0 2025/7/3 14:39
 */
public interface JsonVariableAccessor extends VariableAccessor<String> {

    /**
     * 获取所有变量
     *
     * @return 所有变量
     */
    @Override
    @SuppressWarnings("unchecked")
    default Map<String, Object> getAllVariable() {
        if (StrUtil.isBlank(this.getVariable())) {
            return new HashMap<>();
        }
        return JSON.parseObject(this.getVariable(), Map.class);
    }

    /**
     * 获取变量
     *
     * @param variableKey 变量key
     * @return 变量
     */
    @Override
    default Object getVariableByKey(String variableKey) {
        Map<String, Object> allVariable = this.getAllVariable();
        return allVariable.get(variableKey);
    }

    @Override
    default boolean hasVariable(String variableKey) {
        return this.getVariableByKey(variableKey) != null;
    }

    @Override
    default boolean hasVariableValue(String variableKey, Object variableValue) {
        Object value = this.getVariableByKey(variableKey);
        if (value == null) {
            return false;
        }
        return value.equals(variableValue);
    }

    /**
     * 添加变量
     *
     * @param variable 添加的变量
     */
    @Override
    default void addVariable(Map<String, Object> variable) {
        if (CollectionUtil.isNotEmpty(variable)) {
            Map<String, Object> allVariable = this.getAllVariable();
            allVariable.putAll(variable);
            this.setVariable(JSON.toJSONString(allVariable));
        }
    }

    @Override
    default void addVariable(String variableKey, Object variableValue) {
        if (StrUtil.isNotBlank(variableKey) && variableValue != null) {
            this.addVariable(MapUtil.of(variableKey, variableValue));
        }
    }

    /**
     * 删除变量
     *
     * @param variableKey 删除的变量key
     */
    @Override
    default void removeVariable(String variableKey) {
        Map<String, Object> allVariable = this.getAllVariable();
        allVariable.remove(variableKey);
        this.setVariable(JSON.toJSONString(allVariable));
    }
}
