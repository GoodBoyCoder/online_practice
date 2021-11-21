package com.online.www.pojo.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Lenovo
 */
@Data
public class UserStarBo {

    private Integer userId;

    @NotNull(message = "问题ID不允许为空")
    @ApiModelProperty(value = "问题ID", required = true)
    private Long questionId;

    private String remark;

}
