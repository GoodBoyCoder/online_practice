package com.online.www.pojo.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Lenovo
 */
@Data
public class UserStarBo {

    @ApiModelProperty(value = "账户", required = true, example = "user")
    private Integer userId;

    @ApiModelProperty(value = "账户", required = true, example = "user")
    private Long questionId;

    private String remark;

}
