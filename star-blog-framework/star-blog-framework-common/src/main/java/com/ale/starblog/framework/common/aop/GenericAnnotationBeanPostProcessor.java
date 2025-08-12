package com.ale.starblog.framework.common.aop;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;

/**
 * 通用注解处理器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/7/9
 **/
public class GenericAnnotationBeanPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor {

    /**
     * 构造方法
     *
     * @param advisor                切面增强器
     * @param order                  排序
     * @param beforeExistingAdvisors 是否插入当前advisors之前
     */
    public GenericAnnotationBeanPostProcessor(Advisor advisor, int order, boolean beforeExistingAdvisors) {
        this.advisor = advisor;
        this.setOrder(order);
        this.setBeforeExistingAdvisors(beforeExistingAdvisors);
    }
}
