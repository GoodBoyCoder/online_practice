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
public class ExamQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 试卷ID
     */
    @TableId(value = "exam_id", type = IdType.AUTO)
    private Integer examId;

    /**
     * 题目ID
     */
    @TableField("question_id")
    private Integer questionId;

    /**
     * 题目顺序（题号）
     */
    @TableField("number")
    private Integer number;


    public static final String EXAM_ID = "exam_id";

    public static final String QUESTION_ID = "question_id";

    public static final String NUMBER = "number";

}
