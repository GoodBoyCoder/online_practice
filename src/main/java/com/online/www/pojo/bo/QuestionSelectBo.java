package com.online.www.pojo.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author GoodBoyCoder
 * @date 2021-11-20
 */
@Data
public class QuestionSelectBo {
    /**
     * 科目ID
     */
    @ApiModelProperty(value = "科目ID")
    private Integer subjectId;

    /**
     * 题目类型
     */
    @ApiModelProperty(value = "题目类型(0-单选 1-多选 2-判断)")
    private Integer type;

    /**
     * 是否是图片题(false-文字题/ true-带图片)
     */
    @ApiModelProperty(value = "是否是图片题")
    private Boolean needPic;

    /**
     * 题目章节
     */
    @ApiModelProperty(value = "题目章节")
    private String chapter;

    /**
     * 是否跳过做过且正确的题目
     */
    @ApiModelProperty(value = "是否跳过做过且正确的题目，默认true")
    private Boolean skipRight = true;
}
