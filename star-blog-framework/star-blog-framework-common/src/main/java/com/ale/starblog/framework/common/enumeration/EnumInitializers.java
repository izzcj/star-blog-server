package com.ale.starblog.framework.common.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

/**
 * 枚举初始化器集合
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/25
 */
@Getter
@RequiredArgsConstructor
@Component("enumInitializers")
public class EnumInitializers {

    /**
     * 枚举初始化器
     */
    private final ObjectProvider<EnumInitializer> initializers;
}
