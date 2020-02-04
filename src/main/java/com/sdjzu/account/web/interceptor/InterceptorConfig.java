package com.sdjzu.account.web.interceptor;

import com.sdjzu.account.web.auth.JWTUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author mindgazer
 * @date 2019/06/26
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private JWTUtils JWTUtils;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(JWTUtils))
                // 默认处理所有api
                .addPathPatterns("/api/**")

                // 排除掉login接口
                .excludePathPatterns("/api/**/login");

    }
}
