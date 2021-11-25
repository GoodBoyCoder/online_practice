package com.online.www.pojo.vo;

import com.online.www.pojo.po.Exam;
import com.online.www.pojo.po.ExamUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 考试信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamVo {

    @ApiModelProperty(value = "考试ID")
    private Integer examId;

    @ApiModelProperty(value = "考试名称")
    private String examName;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "持续时间（单位：分钟）")
    private Integer duration;

    @ApiModelProperty(value = "科目")
    private String subject;

    @ApiModelProperty(value = "题数")
    private Integer totalQuestion;

    @ApiModelProperty(value = "答对题数")
    private Integer passQuestion;

    @ApiModelProperty(value = "考试总分")
    private Double totalScore;

    public ExamVo(Exam exam, ExamUser examUser, String subject) {
        this.examId = exam.getId();
        this.examName = exam.getExamName();
        this.startTime = exam.getStartTime();
        this.duration = exam.getDuration();
        this.subject = subject;
        this.totalQuestion = examUser.getTotalQuestion();
        this.passQuestion = examUser.getPassQuestion();
        this.totalScore = examUser.getTotalScore();
    }

}
