package com.ale.starblog.framework.workflow.config;

import com.ale.starblog.framework.workflow.ComponentScanMark;
import com.ale.starblog.framework.workflow.cache.FlowEngineCache;
import com.ale.starblog.framework.workflow.cache.RedisFlowEngineCache;
import com.ale.starblog.framework.workflow.db.SqlStatementsBuilder;
import com.ale.starblog.framework.workflow.db.DatabaseSchemaInitializer;
import com.ale.starblog.framework.workflow.executor.*;
import com.ale.starblog.framework.workflow.hook.InstanceListener;
import com.ale.starblog.framework.workflow.hook.InstanceListenerRegisterer;
import com.ale.starblog.framework.workflow.hook.TaskListener;
import com.ale.starblog.framework.workflow.hook.TaskListenerRegisterer;
import com.ale.starblog.framework.workflow.model.InstanceModelSupportInitializer;
import com.ale.starblog.framework.workflow.model.TaskAssigneeSupportInitializer;
import com.ale.starblog.framework.workflow.parser.*;
import com.ale.starblog.framework.workflow.service.*;
import com.ale.starblog.framework.workflow.service.config.VenusFlowServiceAutoConfiguration;
import com.ale.starblog.framework.workflow.support.*;
import com.ale.starblog.framework.workflow.trigger.DefaultFlowTrigger;
import com.ale.starblog.framework.workflow.trigger.FlowTrigger;
import com.ale.starblog.framework.workflow.trigger.FlowTriggerHolderInitializer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Vef流程引擎自动配置
 *
 * @author Ale
 * @version 1.0.0 2025/6/26 17:34
 */
@AutoConfiguration
@RequiredArgsConstructor
@ComponentScan(basePackageClasses = ComponentScanMark.class)
@Import(VenusFlowServiceAutoConfiguration.class)
@EnableConfigurationProperties(VenusFlowProperties.class)
public class VenusFlowAutoConfiguration {

    /**
     * Venus工作流配置
     */
    private final VenusFlowProperties venusFlowProperties;

    /**
     * 数据库初始化器
     *
     * @param dataSource            数据源
     * @param sqlStatementsBuilders sql片段构建器
     * @return 数据库初始化器Bean
     */
    @Bean
    public DatabaseSchemaInitializer databaseSchemaInitializer(DataSource dataSource, ObjectProvider<SqlStatementsBuilder> sqlStatementsBuilders) {
        return new DatabaseSchemaInitializer(this.venusFlowProperties, dataSource, sqlStatementsBuilders);
    }

    /**
     * 工作流缓存
     *
     * @return 工作流缓存
     */
    @Bean
    @ConditionalOnMissingBean
    public FlowEngineCache flowEngineCache() {
        return new RedisFlowEngineCache();
    }

    /**
     * ID生成器
     *
     * @return ID生成器bean
     */
    @Bean
    @ConditionalOnMissingBean
    public IdGenerator idGenerator() throws Throwable {
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator();
        MethodHandles.privateLookupIn(IdGeneratorSupport.class, MethodHandles.lookup())
            .findStatic(IdGeneratorSupport.class, "setIdGenerator", MethodType.methodType(void.class, IdGenerator.class))
            .invoke(snowflakeIdGenerator);
        return snowflakeIdGenerator;
    }

    /**
     * 实例模型解析器
     *
     * @return 实例模型解析器bean
     */
    @Bean
    @ConditionalOnMissingBean
    public InstanceModelParser instanceModelParser() {
        return new DefaultInstanceModelParser();
    }

    /**
     * 流程实例模型支持类初始化器
     *
     * @return 流程实例模型支持类初始化器Bean
     */
    @Bean
    @ConditionalOnBean(InstanceModelParser.class)
    public static InstanceModelSupportInitializer instanceModelSupportInitializer() {
        return new InstanceModelSupportInitializer();
    }

    /**
     * 表达式构建器
     *
     * @return 表达式构建器bean
     */
    @Bean
    @ConditionalOnMissingBean
    public ExpressionBuilder expressionBuilder() {
        return new SpelExpressionBuilder();
    }

    /**
     * 条件解析器
     *
     * @return 条件解析器bean
     */
    @Bean
    @ConditionalOnMissingBean
    public ConditionParser conditionParser() {
        return new DefaultConditionParser();
    }

    /**
     * 条件执行器
     *
     * @param expressionBuilder 表达式构建器
     * @return 条件执行器bean
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(ExpressionBuilder.class)
    public ConditionExecutor conditionExecutor(ExpressionBuilder expressionBuilder) {
        return new SpelConditionExecutor(expressionBuilder);
    }

    /**
     * 条件执行器支持类初始化器
     *
     * @return 条件执行器支持类初始化器Bean
     */
    @Bean
    @ConditionalOnBean({ConditionExecutor.class, ConditionParser.class})
    public static ConditionExecutorSupportInitializer conditionExecutorSupportInitializer() {
        return new ConditionExecutorSupportInitializer();
    }

    /**
     * 任务受理人支持类初始化器
     *
     * @return 任务分配支持类初始化器Bean
     */
    @Bean
    @ConditionalOnBean(TaskAssigneeParser.class)
    public static TaskAssigneeSupportInitializer taskAssigneeSupportInitializer() {
        return new TaskAssigneeSupportInitializer();
    }

    /**
     * 流程实例监听器注册器
     *
     * @param instanceListenerProvider 流程实例监听器提供器
     * @return 流程实例监听器注册器Bean
     */
    @Bean
    public InstanceListenerRegisterer instanceListenerRegisterer(ObjectProvider<InstanceListener> instanceListenerProvider) {
        return new InstanceListenerRegisterer(instanceListenerProvider);
    }

    /**
     * 任务监听器注册器
     *
     * @param taskListenerObjectProvider 任务监听器提供器
     * @return 任务监听器注册器Bean
     */
    @Bean
    public TaskListenerRegisterer taskListenerRegisterer(ObjectProvider<TaskListener> taskListenerObjectProvider) {
        return new TaskListenerRegisterer(taskListenerObjectProvider);
    }

    /**
     * 默认流程触发器
     *
     * @return 默认流程触发器Bean
     */
    @Bean
    public DefaultFlowTrigger defaultFlowTrigger() {
        return new DefaultFlowTrigger();
    }

    /**
     * 流程触发器注册器
     *
     * @param flowTriggerProvider 流程触发器提供器
     * @return 流程触发器注册器Bean
     */
    @Bean
    public FlowTriggerHolderInitializer flowTriggerHolderInitializer(ObjectProvider<FlowTrigger> flowTriggerProvider) {
        return new FlowTriggerHolderInitializer(flowTriggerProvider);
    }

    /**
     * 流程上下文初始化器
     *
     * @param definitionService 流程定义服务
     * @param instanceService   流程实例服务
     * @param executionService  流程执行服务
     * @param taskService       流程任务服务
     * @param queryService      流程查询服务
     * @return 流程上下文初始化器Bean
     */
    @Bean
    public FlowContextInitializer flowContextInitializer(DefinitionService definitionService, InstanceService instanceService,
                                                         ExecutionService executionService, TaskService taskService, QueryService queryService) {
        return new FlowContextInitializer(this.venusFlowProperties, definitionService, instanceService, executionService, taskService, queryService);
    }

    /**
     * 流程业务实体持有器
     *
     * @return 流程业务实体持有器Bean
     */
    @Bean
    public FlowBusinessEntityHolder flowBusinessEntityHolder() {
        return new FlowBusinessEntityHolder();
    }

}
