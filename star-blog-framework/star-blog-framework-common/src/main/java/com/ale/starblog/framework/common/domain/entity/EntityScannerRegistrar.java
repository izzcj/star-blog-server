package com.ale.starblog.framework.common.domain.entity;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import org.springframework.util.ClassUtils;


/**
 * 实体扫描器注册
 *
 * @author Ale
 * @version 1.0.0 2025/8/5 09:21
 */
public class EntityScannerRegistrar implements ImportBeanDefinitionRegistrar {


    @Override
    public void registerBeanDefinitions(@NonNull AnnotationMetadata importingClassMetadata, @NonNull BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
            importingClassMetadata.getAnnotationAttributes(EntityScan.class.getName())
        );

        if (CollectionUtil.isEmpty(attributes)) {
            return;
        }
        String[] basePackages = attributes.getStringArray("value");
        if (ArrayUtil.isEmpty(basePackages)) {
            basePackages = new String[] {
                ClassUtils.getPackageName(importingClassMetadata.getClassName())
            };
        }
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DefaultEntityManager.class);
        builder.addConstructorArgValue(basePackages);
        builder.addConstructorArgReference("entityInitializers");
        builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);

        registry.registerBeanDefinition(DefaultEntityManager.class.getName(), builder.getBeanDefinition());
    }
}
