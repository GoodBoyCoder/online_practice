package com.online.www.pojo.vo;

import com.online.www.pojo.po.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Yuan
 */
@Data
public class UserVo {

    @ApiModelProperty("用户ID")
    private Integer id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户头像")
    private String pic;

    public UserVo convertFromUser(User user){
        if (user == null){
            return null;
        }
        this.id = user.getId();
        this.userName = user.getUserName();
        this.pic = user.getPic();
        return this;
    }
}
