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
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 科目名
     */
    @TableField("subject_name")
    private String subjectName;

    /**
     * 备注
     */
    @TableField("record")
    private String record;


    public static final String ID = "id";

    public static final String SUBJECT_NAME = "subject_name";

    public static final String RECORD = "record";

}
