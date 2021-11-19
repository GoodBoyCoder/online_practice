package com.online.www.pojo.bo;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author GoodBoyCoder
 * @date 2021-11-19
 */
@Data
public class LoginBo {
    @ApiModelProperty(value = "账户",required = true,example = "user")
    @NotBlank(message = "账号不能为空")
    private String userName;
    @ApiModelProperty(value = "账户密码",required = true,example = "password")
    @NotBlank(message = "密码不能为空")
    private String password;
}
