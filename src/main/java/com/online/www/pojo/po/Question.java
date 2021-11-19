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
    @TableField("option")
    private String option;

    /**
     * 答案
     */
    @TableField("answer")
    private String answer;

    /**
     * 解释
     */
    @TableField("explain")
    private String explain;

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
    @TableField("type")
    private Integer type;


    public static final String ID = "id";

    public static final String QUESTION = "question";

    public static final String OPTION = "option";

    public static final String ANSWER = "answer";

    public static final String EXPLAIN = "explain";

    public static final String PIC = "pic";

    public static final String MARK = "mark";

    public static final String TYPE = "type";

}
