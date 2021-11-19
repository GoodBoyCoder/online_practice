package com.online.www.config;

import com.online.www.handler.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author GoodBoyCoder
 * @date 2021-11-19
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Bean
    public AuthInterceptor authenticationInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");
    }
}
