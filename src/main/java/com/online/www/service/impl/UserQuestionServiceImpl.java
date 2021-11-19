package com.online.www.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.www.mapper.UserQuestionMapper;
import com.online.www.pojo.po.UserQuestion;
import com.online.www.service.UserQuestionService;
import org.springframework.stereotype.Service;

/**
 * @author GoodBoyCoder
 * @date 2021-11-19
 */
@Service
public class UserQuestionServiceImpl extends ServiceImpl<UserQuestionMapper, UserQuestion> implements UserQuestionService {
}
