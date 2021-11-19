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
public class SubjectQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 科目
     */
    @TableField("subject_id")
    private String subjectId;

    /**
     * 题目ID
     */
    @TableField("question_id")
    private Long questionId;


    public static final String ID = "id";

    public static final String SUBJECT_ID = "subject_id";

    public static final String QUESTION_ID = "question_id";

}
