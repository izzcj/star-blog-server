package com.ale.starblog.framework.core.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.AutoMappingUnknownColumnBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * Mybatis-plus拓展自动配置
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/25
 */
@AutoConfiguration
@AutoConfigureBefore(MybatisPlusAutoConfiguration.class)
public class MybatisPlusExtensionAutoConfiguration {

    /**
     * 分页插件
     *
     * @return MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return mybatisPlusInterceptor;
    }

    /**
     * 拓展MetaObjectHandler
     *
     * @return MetaObjectHandler
     */
    @Bean
    @ConditionalOnMissingBean
    public MetaObjectHandler metaObjectHandler() {
        return new AuditMetaObjectHandler();
    }

    /**
     * Mybatis-plus配置
     *
     * @return 配置Bean
     */
    @Bean
    public ConfigurationCustomizer mybatisPlusConfigurationCustomizer() {
        return configuration -> {
            configuration.setMapUnderscoreToCamelCase(true);
            configuration.setAutoMappingBehavior(AutoMappingBehavior.FULL);
            configuration.setAutoMappingUnknownColumnBehavior(AutoMappingUnknownColumnBehavior.WARNING);
            configuration.setLocalCacheScope(LocalCacheScope.SESSION);
            configuration.setCacheEnabled(true);
            configuration.setCallSettersOnNulls(false);
            configuration.setDefaultEnumTypeHandler(EnumTypeHandler.class);
            configuration.setDefaultExecutorType(ExecutorType.REUSE);
        };
    }
}
