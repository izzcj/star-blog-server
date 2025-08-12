package com.ale.starblog.framework.security.support;

import cn.hutool.core.collection.CollectionUtil;
import com.ale.starblog.framework.security.config.VenusSecurityProperties;
import com.ale.starblog.framework.common.enumeration.RequestMethod;
import com.ale.starblog.framework.security.exception.VenusSecurityException;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * 基于配置的需要认证Mvc路径提供器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/28
 **/
@Slf4j
@RequiredArgsConstructor
public class PropertiesAuthenticatedMvcPatternProvider implements AuthenticatedMvcPatternProvider {

    /**
     * 安全配置
     */
    private final VenusSecurityProperties securityProperties;

    @Override
    public List<MvcPattern> provide() {
        List<RequestUriDescriptor> authenticatedUris = this.securityProperties.getAuthenticatedUris();
        if (CollectionUtil.isEmpty(authenticatedUris)) {
            return Collections.emptyList();
        }

        List<MvcPattern> mvcPatterns = Lists.newArrayListWithExpectedSize(authenticatedUris.size());
        for (RequestUriDescriptor descriptor : authenticatedUris) {
            if (CollectionUtil.isEmpty(descriptor.getMethods())) {
                throw new VenusSecurityException("无需认证放行的路径模式[{}]的请求方法不能为空", descriptor.getUri());
            }

            for (RequestMethod method : descriptor.getMethods()) {
                mvcPatterns.add(
                    new MvcPattern(method, descriptor.getUri())
                );
            }
        }

        return mvcPatterns;
    }

}
