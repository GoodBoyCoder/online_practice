package com.online.www.pojo.po;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class UserQuestion implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 题目ID
     */
    @TableField("question_id")
    private Long questionId;

    /**
     * 做题情况
     */
    @TableField("answer")
    private String answer;

    /**
     * 做题结果(分数) get/total
     */
    @TableField("result")
    private String result;

    /**
     * 是否题目完全正确
     */
    @TableField("complete_true")
    private Boolean completeTrue;

    /**
     * 做题时间
     */
    @TableField("modify_time")
    private LocalDateTime modifyTime;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String QUESTION_ID = "question_id";

    public static final String ANSWER = "answer";

    public static final String RESULT = "result";

    public static final String COMPLETE_TRUE = "complete_true";

    public static final String MODIFY_TIME = "modify_time";
}
