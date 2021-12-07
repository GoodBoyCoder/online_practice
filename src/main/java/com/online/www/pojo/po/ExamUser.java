package com.online.www.pojo.po;

import java.io.Serializable;
import java.util.Objects;

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
     * 考试题目数
     */
    @TableField("total_question")
    private Integer totalQuestion;

    /**
     * 答对题目数
     */
    @TableField("pass_question")
    private Integer passQuestion;

    /**
     * 考试总分
     */
    @TableField("total_score")
    private Double totalScore;

    /**
     * 考试时间(分钟)
     */
    @TableField("pass_time")
    private Integer passTime;

    /**
     * 判断是否及格（及格线是60）
     *
     * @return true-及格；false-不及格
     */
    public Boolean isPassing() {
        if (Objects.isNull(totalScore)) {
            return false;
        } else {
            // 及格线固定是90
            return this.totalScore.compareTo(90D) >= 0;
        }
    }
}
