package com.online.www.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "认证的token",required = true)
    private String token;

    /**
     * Token 过期时间（毫秒时间戳）
     */
    @ApiModelProperty(value = "过期时间（毫秒时间戳）",required = true)
    private Long expireAt;
}
