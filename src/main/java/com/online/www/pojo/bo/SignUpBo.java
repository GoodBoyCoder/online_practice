package com.online.www.pojo.bo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author GoodBoyCoder
 * @date 2021-11-20
 */
@Data
public class SignUpBo {
    @ApiModelProperty(value = "用户名", required = true, example = "user")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty(value = "密码", required = true, example = "password",
            notes = "密码至少包含一个数字，一个大写字母，一个小写，一个特殊字符，长度在6~18之间")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,18}$",
            message = "密码至少包含一个数字，一个大写字母，一个小写，一个特殊字符，长度在6~18之间")
    private String password;

    @ApiModelProperty(value = "头像URL地址")
    private String picUrl;
}
