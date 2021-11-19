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
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_name")
    private String userName;

    @TableField("passward")
    private String passward;

    @TableField("pic")
    private String pic;


    public static final String ID = "id";

    public static final String USER_NAME = "user_name";

    public static final String PASSWARD = "passward";

    public static final String PIC = "pic";

}
