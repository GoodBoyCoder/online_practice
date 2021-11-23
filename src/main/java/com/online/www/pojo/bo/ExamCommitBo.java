package com.online.www.pojo.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xql
 */
@Data
public class ExamCommitBo {

    @ApiModelProperty(value = "自动组卷信息")
    @NotNull(message = "自动组卷信息不能为空")
    private CreateExamBo createExamBo;

    @ApiModelProperty(value = "试卷题目列表")
    @NotEmpty(message = "试卷题目列表不能为空")
    private List<QuestionJudgeBo> questionJudgeBoList;
}
