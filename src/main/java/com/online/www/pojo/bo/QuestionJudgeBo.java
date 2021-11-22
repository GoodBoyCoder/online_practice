package com.online.www.pojo.bo;

import javax.validation.constraints.NotNull;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author GoodBoyCoder
 * @date 2021-11-22
 */
@Data
public class QuestionJudgeBo {
    @ApiModelProperty(value = "题目ID", required = true)
    @NotNull(message = "题目ID不能为空")
    private Long questionId;

    @ApiModelProperty(value = "答案集合")
    private List<String> answerList;

    private Integer userId;
}
