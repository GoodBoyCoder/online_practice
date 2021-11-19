package com.online.www.service.impl;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.www.config.SecurityProperties;
import com.online.www.mapper.UserMapper;
import com.online.www.pojo.bo.LoginBo;
import com.online.www.pojo.po.User;
import com.online.www.pojo.vo.LoginVo;
import com.online.www.service.UserService;
import com.online.www.util.JwtUtil;
import com.online.www.util.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author online
 * @since 2021-11-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private SecurityProperties securityProperties;

    @Override
    public LoginVo login(LoginBo loginBo) {
        //验证用户密码
        User user = baseMapper.getUser(loginBo.getUserName());
        Assert.notNull(user, "用户名不存在");
        Assert.isTrue(user.getPassward().equals(MD5Util.getMD5String(loginBo.getPassword())), "密码错误");

        //签发token
        long expireAt = System.currentTimeMillis() + securityProperties.getExpire();
        return new LoginVo(jwtUtil.creatToken(user.getId(), expireAt), expireAt);
    }
}
