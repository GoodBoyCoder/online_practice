package com.online.www.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author GoodBoyCoder
 * @date 2021-11-19
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    /**
     * 鉴权过期时间（单位：毫秒）
     */
    private Long expire = 7200000L;
}
