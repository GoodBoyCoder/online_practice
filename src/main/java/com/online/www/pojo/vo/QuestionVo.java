package com.online.www.pojo.vo;

import java.util.Arrays;
import java.util.List;

import com.online.www.constant.QuestionTypeConstant;
import com.online.www.pojo.po.Question;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @author GoodBoyCoder
 * @date 2021-11-20
 */
@Data
public class QuestionVo {
    @ApiModelProperty(value = "题目ID")
    private Long id;

    /**
     * 问题
     */
    @ApiModelProperty(value = "问题描述")
    private String question;

    /**
     * 选择
     */
    @ApiModelProperty(value = "题目选项")
    private List<String> option;

    /**
     * 答案
     */
    @ApiModelProperty(value = "题目答案")
    private String answer;

    /**
     * 解释
     */
    @ApiModelProperty(value = "题目解析")
    private String explain;

    /**
     * 图片
     */
    @ApiModelProperty(value = "题目图片")
    private String pic;

    /**
     * 题目分数
     */
    @ApiModelProperty(value = "题目分数")
    private Double mark;

    /**
     * 类型（0-单选 1-多选 2-判断）
     */
    @ApiModelProperty(value = "题目类型（0-单选 1-多选 2-判断）")
    private Integer type;

    /**
     * 科目
     */
    @ApiModelProperty(value = "题目所属科目")
    private Integer subjectId;

    /**
     * 章节
     */
    @ApiModelProperty(value = "题目章节")
    private String chapter;

    /**
     * 题目备注
     */
    @ApiModelProperty(value = "题目备注")
    private String remark;


    public void convertFromQuestionWithNoAnswer(Question question) {
        this.id = question.getId();
        this.question = question.getQuestion();
        this.type = question.getQuestionType();
        if (type.equals(QuestionTypeConstant.SINGLE) || type.equals(QuestionTypeConstant.MULTIPLE)) {
            //选择题
            String option = question.getQuestionOptions();
            if (StringUtils.isEmpty(option)) {
                throw new RuntimeException("获取题目异常，请稍后再试");
            }
            this.option = Arrays.asList(option.split("#"));
        }
        this.pic = question.getPic();
        this.mark = question.getMark();
        this.subjectId = question.getSubjectId();
        this.chapter = question.getChapter();
        this.remark = question.getRemark();
    }

    public void convertFromQuestion(Question question) {
        convertFromQuestionWithNoAnswer(question);
        this.answer = question.getAnswer();
        this.explain = question.getQuestionExplain();
    }

}
