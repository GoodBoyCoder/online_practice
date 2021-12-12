package com.online.www.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 考试分析结果
 *
 * @author spice
 * @date 2021/12/12 19:20
 */
@Data
public class ExamAnalysisVo {

    @ApiModelProperty(value = "科目id")
    private Integer subjectId;

    @ApiModelProperty(value = "科目名称")
    private String subjectName;

    @ApiModelProperty(value = "考试次数")
    private Integer examCount;

    @ApiModelProperty(value = "考试通过次数")
    private Integer passingCount;

    @ApiModelProperty(value = "预测通过率")
    private Double rate;
}
