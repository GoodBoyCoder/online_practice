package com.online.www.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author GoodBoyCoder
 * @date 2021-11-22
 */
@Data
public class QuestionJudgeVo {
    @ApiModelProperty(value = "题目id")
    private Long questionId;
    @ApiModelProperty(value = "答案")
    private String answer;
    @ApiModelProperty(value = "题解")
    private String explain;
    @ApiModelProperty(value = "是否正确")
    private Boolean correct;

    @ApiModelProperty(hidden = true)
    private String myAnswer;
}
