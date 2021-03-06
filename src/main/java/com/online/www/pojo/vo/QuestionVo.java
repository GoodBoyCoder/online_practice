package com.online.www.pojo.vo;

import java.util.Arrays;
import java.util.List;

import com.online.www.constant.QuestionTypeConstant;
import com.online.www.pojo.po.Question;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

/**
 * @author GoodBoyCoder
 * @date 2021-11-20
 */
@Data
@Accessors(chain = true)
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
     * 类型（1-判断 2-单选 3-多选）
     */
    @ApiModelProperty(value = "题目类型（1-判断 2-单选 3-多选 ）")
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

    /**
     * 题目是否已经收藏（true-已经收藏）
     */
    @ApiModelProperty(value = "题目是否已经收藏（true-已经收藏）")
    private Boolean stared;

    @ApiModelProperty(value = "我的答案")
    private List<String> myAnswer;


    public QuestionVo convertFromQuestionWithNoAnswer(Question question) {
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
        } else if (type.equals(QuestionTypeConstant.JUDGE)) {
            this.option = Arrays.asList("对", "错");
        }
        this.pic = question.getPic();
        this.mark = question.getMark();
        this.subjectId = question.getSubjectId();
        this.chapter = question.getChapter();
        this.remark = question.getRemark();

        return this;
    }

    public QuestionVo convertFromQuestion(Question question) {
        convertFromQuestionWithNoAnswer(question);
        this.answer = question.getAnswer();
        this.explain = question.getQuestionExplain();
        return this;
    }

    public QuestionVo convertStarFromQuestion(Question question){
        convertFromQuestion(question);
        this.setStared(true);
        return this;
    }

}
