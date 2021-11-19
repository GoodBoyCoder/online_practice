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
public class Exam implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 考试名称
     */
    @TableField("exam_name")
    private String examName;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 持续时间（单位/分钟）
     */
    @TableField("duration")
    private Integer duration;

    @TableField("creat_time")
    private LocalDateTime creatTime;

    @TableField("subject_id")
    private Integer subjectId;


    public static final String ID = "id";

    public static final String EXAM_NAME = "exam_name";

    public static final String START_TIME = "start_time";

    public static final String DURATION = "duration";

    public static final String CREAT_TIME = "creat_time";

    public static final String SUBJECT_ID = "subject_id";

}
