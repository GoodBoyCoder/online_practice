package com.online.www.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author GoodBoyCoder
 * @date 2021-11-19
 */
@Data
@AllArgsConstructor
public class LoginVo {
    /**
     * 认证的token
     */
    private String token;

    /**
     * Token 过期时间（毫秒时间戳）
     */
    private Long expireAt;
}
