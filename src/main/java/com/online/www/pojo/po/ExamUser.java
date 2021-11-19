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
public class ExamUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 考试ID
     */
    @TableField("exam_id")
    private Integer examId;

    /**
     * 做题情况ID（user_question）
     */
    @TableField("make_id")
    private Long makeId;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String EXAM_ID = "exam_id";

    public static final String MAKE_ID = "make_id";

}
