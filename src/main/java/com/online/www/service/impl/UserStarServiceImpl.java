package com.online.www.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.www.mapper.QuestionMapper;
import com.online.www.mapper.UserStarMapper;
import com.online.www.pojo.bo.UserStarBo;
import com.online.www.pojo.po.UserStar;
import com.online.www.pojo.vo.QuestionVo;
import com.online.www.service.UserStarService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * @author Lenovo
 */
@Service
public class UserStarServiceImpl extends ServiceImpl<UserStarMapper, UserStar> implements UserStarService {

    @Resource
    private UserStarMapper userStarMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Override
    public Boolean collectionQuestion(UserStarBo userStarBo) {
        Assert.notNull(questionMapper.selectById(userStarBo.getQuestionId()), "该题目不存在！");
        Assert.isNull(userStarMapper.selectByQuestionId(userStarBo.getUserId(), userStarBo.getQuestionId()), "题目已收藏！");
        return userStarMapper.insert(new UserStar(userStarBo.getUserId(), userStarBo.getQuestionId())) == 1;
    }

    @Override
    public QuestionVo selectCollection(Integer questionId) {
        return null;
    }
}
