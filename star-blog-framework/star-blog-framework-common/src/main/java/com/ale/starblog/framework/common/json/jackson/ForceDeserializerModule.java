package com.ale.starblog.framework.common.json.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

/**
 * 强制创建Bean模块
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/4
 */
@Component
public class ForceDeserializerModule extends SimpleModule {

    @Override
    public String getModuleName() {
        return "ForceDeserializerModule";
    }

    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);

        context.addBeanDeserializerModifier(
            new ForceCreatorBeanDeserializerModifier()
        );
    }
}
