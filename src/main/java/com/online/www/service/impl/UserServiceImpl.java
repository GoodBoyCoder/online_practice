package com.online.www.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.www.mapper.UserMapper;
import com.online.www.pojo.po.User;
import com.online.www.service.UserService;
import org.springframework.stereotype.Service;

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

}
