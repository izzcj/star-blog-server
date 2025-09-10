package com.ale.starblog.framework.common.enumeration;

import cn.hutool.core.util.ArrayUtil;
import com.ale.starblog.framework.common.utils.ClassUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

/**
 * 枚举扫描器注册
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/25
 */
public class EnumScannerRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, @NonNull BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
            importingClassMetadata.getAnnotationAttributes(EnumScan.class.getName())
        );

        String[] basePackages = attributes.getStringArray("value");
        if (ArrayUtil.isEmpty(basePackages)) {
            basePackages = new String[] {
                ClassUtils.getPackageName(importingClassMetadata.getClassName())
            };
        }
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(EnumScanner.class);
        builder.addConstructorArgValue(basePackages);
        builder.addConstructorArgReference("enumInitializers");
        builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);

        registry.registerBeanDefinition(EnumScanner.class.getName(), builder.getBeanDefinition());
    }
}
