package com.ale.starblog.framework.common.aop;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.aopalliance.aop.Advice;
import org.springframework.aop.*;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import java.lang.annotation.Annotation;

/**
 * 注解方法切面增强器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/7/9
 **/
@Setter
@EqualsAndHashCode(callSuper = true)
public class AnnotationMethodMatcherPointcutAdvisor extends AnnotationMethodMatcherPointcut implements PointcutAdvisor, Ordered {

    /**
     * 切面
     */
    private Advice advice = EMPTY_ADVICE;

    /**
     * 排序
     */
    private int order = Ordered.LOWEST_PRECEDENCE;

    /**
     * 是否每个实例一个切面
     */
    private boolean perInstance = true;

    public AnnotationMethodMatcherPointcutAdvisor(Class<? extends Annotation> annotationType) {
        super(annotationType);
    }


    public AnnotationMethodMatcherPointcutAdvisor(Class<? extends Annotation> annotationType, boolean checkInherited) {
        super(annotationType, checkInherited);
    }

    public AnnotationMethodMatcherPointcutAdvisor(Class<? extends Annotation> annotationType, Advice advice) {
        super(annotationType);
        this.advice = advice;
    }

    public AnnotationMethodMatcherPointcutAdvisor(Class<? extends Annotation> annotationType, boolean checkInherited, Advice advice) {
        super(annotationType, checkInherited);
        this.advice = advice;
    }

    @NonNull
    @Override
    public Pointcut getPointcut() {
        return this;
    }

    @NonNull
    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public boolean isPerInstance() {
        return this.perInstance;
    }

    @Override
    public int getOrder() {
        return this.order;
    }
}
