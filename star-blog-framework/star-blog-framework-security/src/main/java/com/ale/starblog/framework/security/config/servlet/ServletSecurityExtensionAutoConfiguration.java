package com.ale.starblog.framework.security.config.servlet;

import com.ale.starblog.framework.common.aop.AnnotationMethodMatcherPointcutAdvisor;
import com.ale.starblog.framework.common.aop.GenericAnnotationBeanPostProcessor;
import com.ale.starblog.framework.common.security.PermissionCheck;
import com.ale.starblog.framework.security.authentication.*;
import com.ale.starblog.framework.security.authorization.method.AlwaysAllowedMethodPermissionChecker;
import com.ale.starblog.framework.security.authorization.method.MethodPermissionChecker;
import com.ale.starblog.framework.security.authorization.method.PermissionCheckingMethodInterceptor;
import com.ale.starblog.framework.security.authorization.servlet.AlwaysAllowedRequestPermissionChecker;
import com.ale.starblog.framework.security.authorization.servlet.RequestPermissionChecker;
import com.ale.starblog.framework.security.authorization.servlet.VenusAccessDeniedHandler;
import com.ale.starblog.framework.security.authorization.servlet.VenusAuthorizationManager;
import com.ale.starblog.framework.security.config.VenusSecurityAutoConfiguration;
import com.ale.starblog.framework.security.config.VenusSecurityProperties;
import com.ale.starblog.framework.security.contanst.SecurityConstants;
import com.ale.starblog.framework.common.enumeration.RequestMethod;
import com.ale.starblog.framework.security.support.AuthenticatedMvcPatternProvider;
import com.ale.starblog.framework.security.support.MvcPattern;
import com.ale.starblog.framework.security.support.PermittedMvcPatternProvider;
import com.ale.starblog.framework.security.token.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.SecurityContextRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servlet安全自动配置
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/28
 **/
@AutoConfiguration
@RequiredArgsConstructor
@AutoConfigureAfter(VenusSecurityAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ServletSecurityExtensionAutoConfiguration {
    /**
     * 安全配置
     */
    private final VenusSecurityProperties securityProperties;

    /**
     * 安全上下文仓库
     *
     * @param tokenManager Token管理器
     * @return 安全上下文仓库Bean
     */
    @Bean
    public VenusSecurityContextRepository venusSecurityContextRepository(TokenManager tokenManager) {
        return new VenusSecurityContextRepository(tokenManager, this.securityProperties.isRefreshTokenExpirationPerRequest());
    }

    /**
     * 访问被拒处理器
     *
     * @return 访问被拒处理器Bean
     */
    @Bean
    public VenusAccessDeniedHandler venusAccessDeniedHandler() {
        return new VenusAccessDeniedHandler();
    }

    /**
     * 认证入口
     *
     * @return 认证入口Bean
     */
    @Bean
    public VenusAuthenticationEntryPoint venusAuthenticationEntryPoint() {
        return new VenusAuthenticationEntryPoint();
    }

    /**
     * 认证成功处理器
     *
     * @return 认证成功处理器Bean
     */
    @Bean
    public VenusAuthenticationSuccessHandler venusAuthenticationSuccessHandler() {
        return new VenusAuthenticationSuccessHandler();
    }

    /**
     * 认证失败处理器
     *
     * @return 认证失败处理器Bean
     */
    @Bean
    public VenusAuthenticationFailureHandler venusAuthenticationFailureHandler() {
        return new VenusAuthenticationFailureHandler();
    }

    /**
     * 登出处理器
     *
     * @param tokenManager     Token管理器
     * @param logoutProcessors 登出处理器提供器
     * @return 登出处理器Bean
     */
    @Bean
    public VenusLogoutHandler venusLogoutHandler(TokenManager tokenManager, ObjectProvider<LogoutProcessor> logoutProcessors) {
        return new VenusLogoutHandler(tokenManager, logoutProcessors);
    }

    /**
     * 登出成功处理器
     *
     * @return 登出成功处理器Bean
     */
    @Bean
    public VenusLogoutSuccessHandler venusLogoutSuccessHandler() {
        return new VenusLogoutSuccessHandler();
    }

    /**
     * 密码加密器
     *
     * @return 密码加密Bean
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 登录配置
     *
     * @param logoutHandler        登出处理器
     * @param logoutSuccessHandler 登出成功处理器
     * @return 登录配置Bean
     */
    @Bean
    public LoginHttpSecurityConfigurer loginHttpSecurityConfigurer(VenusLogoutHandler logoutHandler, VenusLogoutSuccessHandler logoutSuccessHandler) {
        return new LoginHttpSecurityConfigurer(logoutHandler, logoutSuccessHandler);
    }

    /**
     * 聚合认证安全配置器
     *
     * @param authenticationSuccessHandler 认证成功处理器
     * @param authenticationFailureHandler 认证失败处理器
     * @param loginProcessorsHolder        登录处理器持有器
     * @return 聚合认证安全配置Bean
     */
    @Bean
    public CompositeAuthenticationHttpSecurityConfigurer compositeAuthenticationHttpSecurityConfigurer(AuthenticationSuccessHandler authenticationSuccessHandler,
                                                                                                       AuthenticationFailureHandler authenticationFailureHandler,
                                                                                                       LoginProcessorsHolder loginProcessorsHolder) {
        return new CompositeAuthenticationHttpSecurityConfigurer(authenticationSuccessHandler, authenticationFailureHandler, loginProcessorsHolder);
    }

    /**
     * 默认不进行请求权限检查
     *
     * @return 权限检查Bean
     */
    @Bean
    @ConditionalOnMissingBean
    public RequestPermissionChecker requestPermissionChecker() {
        return new AlwaysAllowedRequestPermissionChecker();
    }

    /**
     * 默认不进行方法权限检查
     *
     * @return 权限检查Bean
     */
    @Bean
    @ConditionalOnMissingBean
    public MethodPermissionChecker methodPermissionChecker() {
        return new AlwaysAllowedMethodPermissionChecker();
    }

    /**
     * 安全配置
     *
     * @param permittedMvcPatternProviders     无需认证的请求路径模式提供器
     * @param securityContextRepository        安全上下文仓库
     * @param authenticatedMvcPatternProviders 需要认证的请求路径模式提供器
     * @param authenticationEntryPoint         认证入口
     * @param accessDeniedHandler              访问拒绝处理器
     * @param httpSecurityConfigurers          http安全配置器
     * @param permissionCheckers               权限检查器
     * @param http http安全对象
     * @return 安全过滤器链对象
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
        ObjectProvider<PermittedMvcPatternProvider> permittedMvcPatternProviders,
        SecurityContextRepository securityContextRepository,
        ObjectProvider<AuthenticatedMvcPatternProvider> authenticatedMvcPatternProviders,
        AuthenticationEntryPoint authenticationEntryPoint,
        AccessDeniedHandler accessDeniedHandler,
        ObjectProvider<HttpSecurityConfigurer> httpSecurityConfigurers,
        ObjectProvider<RequestPermissionChecker> permissionCheckers,
        HttpSecurity http) throws Exception {

        http.securityContext(configurer -> configurer
                .requireExplicitSave(true)
                .securityContextRepository(securityContextRepository)
            )
            .exceptionHandling(configurer -> configurer
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
            )
            .servletApi(configurer -> configurer.rolePrefix(SecurityConstants.ROLE_PREFIX))
            // 以下配置均可实现HttpSecurityConfigurer来自定义配置
            .formLogin(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .requestCache(AbstractHttpConfigurer::disable)
            .headers(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .rememberMe(AbstractHttpConfigurer::disable)
            .anonymous(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(requestMatcherRegistry -> {
            this.registerPermittedMvcPatterns(permittedMvcPatternProviders, requestMatcherRegistry);
            this.registerAuthenticatedMvcPatterns(authenticatedMvcPatternProviders, requestMatcherRegistry);
            requestMatcherRegistry
                .anyRequest()
                .access(new VenusAuthorizationManager(permissionCheckers));
        });

        for (HttpSecurityConfigurer httpSecurityConfigurer : httpSecurityConfigurers) {
            httpSecurityConfigurer.configure(http);
        }

        return http.build();
    }

    /**
     * 注册需要认证的请求
     *
     * @param authenticatedMvcPatternProviders mvc路径模式提供器
     * @param requestMatcherRegistry 请求匹配注册器
     */
    private void registerAuthenticatedMvcPatterns(ObjectProvider<AuthenticatedMvcPatternProvider> authenticatedMvcPatternProviders, AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry requestMatcherRegistry) {
        Map<RequestMethod, List<MvcPattern>> authenticatedMvcPatternMap = authenticatedMvcPatternProviders.stream()
            .flatMap(authenticatedMvcPatternProvider -> authenticatedMvcPatternProvider.provide().stream())
            .collect(Collectors.groupingBy(MvcPattern::getMethod));

        for (Map.Entry<RequestMethod, List<MvcPattern>> entry : authenticatedMvcPatternMap.entrySet()) {
            requestMatcherRegistry.requestMatchers(
                entry.getKey() == RequestMethod.ALL
                    ? null
                    : HttpMethod.valueOf(entry.getKey().name()),
                entry.getValue()
                    .stream()
                    .map(MvcPattern::getPattern)
                    .toArray(String[]::new)
            ).authenticated();
        }
    }

    /**
     * 注册无需认证的请求
     *
     * @param permittedMvcPatternProviders mvc路径模式提供器
     * @param requestMatcherRegistry 请求匹配注册器
     */
    private void registerPermittedMvcPatterns(ObjectProvider<PermittedMvcPatternProvider> permittedMvcPatternProviders, AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry requestMatcherRegistry) {
        Map<RequestMethod, List<MvcPattern>> permittedMvcPatternMap = permittedMvcPatternProviders.stream()
            .flatMap(permittedMvcPatternProvider -> permittedMvcPatternProvider.provide().stream())
            .collect(Collectors.groupingBy(MvcPattern::getMethod));
        for (Map.Entry<RequestMethod, List<MvcPattern>> entry : permittedMvcPatternMap.entrySet()) {
            requestMatcherRegistry.requestMatchers(
                entry.getKey() == RequestMethod.ALL
                    ? null
                    : HttpMethod.valueOf(entry.getKey().name()),
                entry.getValue()
                    .stream()
                    .map(MvcPattern::getPattern)
                    .toArray(String[]::new)
            ).permitAll();
        }
    }

    /**
     * 方法权限检查处理器
     *
     * @param methodPermissionCheckers 方法权限检查器
     * @return 处理器Bean
     */
    @Bean
    public GenericAnnotationBeanPostProcessor methodPermissionCheckingAnnotationBeanPostProcessor(ObjectProvider<MethodPermissionChecker> methodPermissionCheckers) {
        AnnotationMethodMatcherPointcutAdvisor advisor = new AnnotationMethodMatcherPointcutAdvisor(PermissionCheck.class, false);
        var methodInterceptor = new PermissionCheckingMethodInterceptor(methodPermissionCheckers);
        advisor.setAdvice(methodInterceptor);

        return new GenericAnnotationBeanPostProcessor(advisor, Ordered.HIGHEST_PRECEDENCE + 600, true);
    }
}
