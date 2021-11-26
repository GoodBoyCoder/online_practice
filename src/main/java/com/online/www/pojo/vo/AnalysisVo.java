package com.online.www.pojo.vo;

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
}
