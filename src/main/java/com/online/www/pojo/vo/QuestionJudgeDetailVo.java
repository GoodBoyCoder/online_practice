package com.online.www.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 判题详情
 * @author xql
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionJudgeDetailVo {

    @ApiModelProperty(value = "题目")
    private QuestionVo questionVo;

    @ApiModelProperty(value = "判题结果")
    private QuestionJudgeVo questionJudgeVo;

}
