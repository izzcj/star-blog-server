package com.ale.starblog.framework.common.transaction;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.transaction.PlatformTransactionManager;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * 事务支持初始化器
 *
 * @author Ale
 * @version 1.0.0 2025/6/25 9:08
 */
public class TransactionSupportInitializer implements SmartInitializingSingleton {

    /**
     * 事务管理器
     */
    private final PlatformTransactionManager platformTransactionManager;

    public TransactionSupportInitializer(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    @Override
    public void afterSingletonsInstantiated() {
        try {
            MethodHandles.privateLookupIn(TransactionSupport.class, MethodHandles.lookup())
                .findStatic(TransactionSupport.class, "setTransactionManager", MethodType.methodType(void.class, PlatformTransactionManager.class))
                .invoke(platformTransactionManager);
        } catch (Throwable e) {
            throw new RuntimeException("初始化TransactionSupport失败!");
        }
    }
}
