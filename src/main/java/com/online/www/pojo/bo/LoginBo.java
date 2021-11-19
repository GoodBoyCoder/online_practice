package com.online.www.pojo.bo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @author GoodBoyCoder
 * @date 2021-11-19
 */
@Data
public class LoginBo {
    @NotBlank(message = "账号不能为空")
    private String userName;
    @NotBlank(message = "密码不能为空")
    private String password;
}
