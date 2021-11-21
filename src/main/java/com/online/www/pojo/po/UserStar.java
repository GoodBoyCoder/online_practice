package com.online.www.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Lenovo
 */
@Data
public class UserStar {

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
     * 题目ID
     */
    @TableField("remark")
    private String remark;

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String QUESTION_ID = "question_id";

    public static final String REMARK = "remark";

    public UserStar(Integer userId, Long questionId) {
        this.userId = userId;
        this.questionId = questionId;
    }
}
