package com.online.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.online.www.pojo.bo.LoginBo;
import com.online.www.pojo.po.User;
import com.online.www.pojo.vo.LoginVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author online
 * @since 2021-11-19
 */
public interface UserService extends IService<User> {
    /**
     * 用户登录
     * @param loginBo 登录用户/密码
     * @return loginVO/token
     */
    LoginVo login(LoginBo loginBo);
}
