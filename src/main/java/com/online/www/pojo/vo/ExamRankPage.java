package com.online.www.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author GoodBoyCoder
 * @date 2021-12-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamRankPage {
    @ApiModelProperty(value = "排名")
    private Integer rank;
    @ApiModelProperty(value = "考试分数")
    private Double score;
    @ApiModelProperty(value = "考试通过时间")
    private Integer passTime;
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "头像")
    private String pic;
}
