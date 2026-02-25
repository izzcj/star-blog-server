package com.ale.starblog.framework.workflow.model;

import com.ale.starblog.framework.workflow.cache.FlowEngineCache;
import com.ale.starblog.framework.workflow.parser.InstanceModelParser;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * 流程实例模型支持类初始化器
 *
 * @author Ale
 * @version 1.0.0 2025/6/12 9:12
 */
public class InstanceModelSupportInitializer implements SmartInitializingSingleton {

    /**
     * 流程实例模型解析器
     */
    private final InstanceModelParser instanceModelParser;

    /**
     * 流程引擎缓存
     */
    private final FlowEngineCache flowEngineCache;

    public InstanceModelSupportInitializer(InstanceModelParser instanceModelParser, FlowEngineCache flowEngineCache) {
        this.instanceModelParser = instanceModelParser;
        this.flowEngineCache = flowEngineCache;
    }

    @Override
    public void afterSingletonsInstantiated() {
        try {
            MethodHandles.privateLookupIn(InstanceModelSupport.class, MethodHandles.lookup())
                .findStatic(InstanceModelSupport.class, "setInstanceModelParser", MethodType.methodType(void.class, InstanceModelParser.class))
                .invoke(instanceModelParser);
            MethodHandles.privateLookupIn(InstanceModelSupport.class, MethodHandles.lookup())
                .findStatic(InstanceModelSupport.class, "setFlowEngineCache", MethodType.methodType(void.class, FlowEngineCache.class))
                .invoke(flowEngineCache);
        } catch (Throwable e) {
            throw new RuntimeException("初始化流程实例模型支持类失败！");
        }
    }
}
