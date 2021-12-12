package com.online.www.pojo.vo;

import com.online.www.pojo.po.Subject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 考试分析结果
 *
 * @author spice
 * @date 2021/11/26 13:30
 */
@Data
@Accessors(chain = true)
public class AnalysisVo {

    @ApiModelProperty(value = "科目id")
    private Integer subjectId;

    @ApiModelProperty(value = "科目名称")
    private String subjectName;

    @ApiModelProperty(value = "预测通过率")
    private Double rate;


    @ApiModelProperty(value = "所做总题目数")
    private Integer totalCount;

    @ApiModelProperty(value = "总正确率")
    private Double totalPassRate;

    @ApiModelProperty(value = "总通过题数")
    private Integer totalPassCount;

    @ApiModelProperty(value = "总错误题数")
    private Integer totalErrorCount;


    @ApiModelProperty(value = "判断题总题数")
    private Integer judgeTotalCount;

    @ApiModelProperty(value = "判断题的正确率")
    private Double judgePassRate;

    @ApiModelProperty(value = "判断题通过题数")
    private Integer judgePassCount;

    @ApiModelProperty(value = "判断题错误题数")
    private Integer judgeErrorCount;


    @ApiModelProperty(value = "单选题总题数")
    private Integer singleTotalCount;

    @ApiModelProperty(value = "单选题的正确率")
    private Double singlePassRate;

    @ApiModelProperty(value = "单选题通过题数")
    private Integer singlePassCount;

    @ApiModelProperty(value = "单选题错误题数")
    private Integer singleErrorCount;


    @ApiModelProperty(value = "多选题总题数")
    private Integer multipleTotalCount;

    @ApiModelProperty(value = "多选题的正确率")
    private Double multiplePassRate;

    @ApiModelProperty(value = "多选题通过题数")
    private Integer multiplePassCount;

    @ApiModelProperty(value = "多选题错误题数")
    private Integer multipleErrorCount;

    /**
     * 无数据，默认分析构造器
     * @param subject 科目
     * @return 都为0的AnalysisVo
     */
    public static AnalysisVo getNoDataAnalysisVo(Subject subject) {
        return new AnalysisVo()
                .setSubjectId(subject.getId())
                .setSubjectName(subject.getSubjectName())
                .setRate(0.0)

                .setTotalCount(0)
                .setTotalPassRate(0.0)
                .setTotalPassCount(0)
                .setTotalErrorCount(0)

                .setJudgeTotalCount(0)
                .setJudgePassRate(0.0)
                .setJudgePassCount(0)
                .setJudgeErrorCount(0)

                .setSingleTotalCount(0)
                .setSinglePassRate(0.0)
                .setSinglePassCount(0)
                .setSingleErrorCount(0)

                .setMultipleTotalCount(0)
                .setMultiplePassRate(0.0)
                .setMultiplePassCount(0)
                .setMultipleErrorCount(0);
    }

    public static AnalysisVo getInstance() {
        return new AnalysisVo();
    }
}
