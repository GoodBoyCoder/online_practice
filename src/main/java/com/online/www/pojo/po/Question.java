package com.online.www.pojo.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author online
 * @since 2021-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 问题
     */
    @TableField("question")
    private String question;

    /**
     * 选择
     */
    @TableField("question_options")
    private String questionOptions;

    /**
     * 答案
     */
    @TableField("answer")
    private String answer;

    /**
     * 解释
     */
    @TableField("question_explain")
    private String questionExplain;

    /**
     * 图片
     */
    @TableField("pic")
    private String pic;

    /**
     * 题目分数
     */
    @TableField("mark")
    private Double mark;

    /**
     * 类型（0-单选 1-多选 2-判断）
     */
    @TableField("question_type")
    private Integer questionType;

    /**
     * 科目
     */
    @TableField("subject_id")
    private Integer subjectId;

    /**
     * 章节
     */
    @TableField("chapter")
    private String chapter;

    /**
     * 题目备注
     */
    @TableField("remark")
    private String remark;


    public static final String ID = "id";

    public static final String QUESTION = "question";

    public static final String QUESTION_OPTIONS = "question_options";

    public static final String ANSWER = "answer";

    public static final String QUESTION_EXPLAIN = "question_explain";

    public static final String PIC = "pic";

    public static final String MARK = "mark";

    public static final String QUESTION_TYPE = "question_type";

    public static final String SUBJECT_ID = "subject_id";

    public static final String CHAPTER = "chapter";

    public static final String REMARK = "remark";

}
