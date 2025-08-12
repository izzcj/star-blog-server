package com.ale.starblog.framework.common.aop;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.annotation.AnnotationMethodMatcher;
import org.springframework.lang.NonNull;
import java.lang.annotation.Annotation;

/**
 * 注解方法匹配器切点实现
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/7/9
 **/
@Setter
@EqualsAndHashCode(callSuper = true)
public class AnnotationMethodMatcherPointcut extends AnnotationMethodMatcher implements Pointcut {

    /**
     * 类过滤器
     */
    private ClassFilter classFilter = ClassFilter.TRUE;

    public AnnotationMethodMatcherPointcut(Class<? extends Annotation> annotationType) {
        super(annotationType);
    }

    public AnnotationMethodMatcherPointcut(Class<? extends Annotation> annotationType, boolean checkInherited) {
        super(annotationType, checkInherited);
    }

    @NonNull
    @Override
    public ClassFilter getClassFilter() {
        return this.classFilter;
    }

    @NonNull
    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }

}
