package com.online.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.online.www.pojo.bo.LoginBo;
import com.online.www.pojo.bo.SignUpBo;
import com.online.www.pojo.po.User;
import com.online.www.pojo.vo.LoginVo;
import com.online.www.pojo.vo.UserVo;

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
     *
     * @param loginBo 登录用户/密码
     * @return loginVO/token
     */
    LoginVo login(LoginBo loginBo);

    /**
     * 用户注册
     *
     * @param signUpBo 注册Bo
     * @return loginVo/token
     */
    LoginVo signUp(SignUpBo signUpBo);

    /**
     * 检查用户名是否合法
     *
     * @param userName 用户名
     * @return 是否合法
     */
    Boolean checkUserName(String userName);

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return UserVo
     */
    UserVo getUserInfo(Integer userId);

}
