package com.ale.starblog.framework.common.transaction;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Venus事务自动配置类
 *
 * @author Ale
 * @version 1.0.0 2025/6/24 14:16
 */
@AutoConfiguration
public class VenusTransactionAutoConfiguration {

    /**
     * 事务支持初始化器
     *
     * @return 事务支持类初始化器bean
     */
    @Bean
    public TransactionSupportInitializer transactionSupportInitializer() {
        return new TransactionSupportInitializer();
    }

}
