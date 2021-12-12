package com.online.www.pojo.vo;

import com.online.www.pojo.po.Subject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 考试分析结果
 *
 * @author spice
 * @date 2021/11/26 13:30
 */
@Data
public class AnalysisVo {

    @ApiModelProperty(value = "科目id")
    private Integer subjectId;

    @ApiModelProperty(value = "科目名称")
    private String subjectName;

    @ApiModelProperty(value = "预测通过率")
    private Double rate;

    @ApiModelProperty(value = "所做题目数")
    private Integer questionCount;

    @ApiModelProperty(value = "总正确率")
    private Double passRate;

    @ApiModelProperty(value = "判断题的正确率")
    private Double judgmentPassRate;

    @ApiModelProperty(value = "单选题的正确率")
    private Double singleChoicePassRate;

    @ApiModelProperty(value = "多选题的正确率")
    private Double multipleChoicePassRate;

    /**
     * 无数据，默认分析构造器
     * @param subject
     * @return
     */
    public static AnalysisVo getNoDataAnalysisVo(Subject subject) {
        AnalysisVo analysisVo = new AnalysisVo();
        analysisVo.setSubjectId(subject.getId());
        analysisVo.setSubjectName(subject.getSubjectName());
        analysisVo.setRate(0.0);
        analysisVo.setJudgmentPassRate(0.0);
        analysisVo.setMultipleChoicePassRate(0.0);
        analysisVo.setSingleChoicePassRate(0.0);
        analysisVo.setQuestionCount(0);
        analysisVo.setPassRate(0.0);
        return analysisVo;
    }
}
