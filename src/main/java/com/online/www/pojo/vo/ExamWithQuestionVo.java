package com.online.www.pojo.vo;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author GoodBoyCoder
 * @date 2021-11-23
 */
@Data
public class ExamWithQuestionVo {
    @ApiModelProperty(value = "考试题目集合")
    List<QuestionVo> questionList;

    @ApiModelProperty(value = "考试名称")
    private String examName;
    @ApiModelProperty(value = "考试开始时间")
    private LocalDateTime startTime;
    @ApiModelProperty(value = "考试持续时间")
    private Integer duration;
    @ApiModelProperty(value = "考试科目")
    private String subject;
}
