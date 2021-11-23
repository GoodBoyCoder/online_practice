package com.online.www.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.www.mapper.QuestionMapper;
import com.online.www.mapper.UserStarMapper;
import com.online.www.pojo.bo.UserStarBo;
import com.online.www.pojo.po.Question;
import com.online.www.pojo.po.UserStar;
import com.online.www.pojo.vo.QuestionVo;
import com.online.www.service.UserStarService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public Page<QuestionVo> getStarQuestion(Integer userId, Integer currentPage, Integer size) {
        Page<UserStar> userStarList = userStarMapper.selectStarQuestionPageByUserId(userId, currentPage, size);
        List<Long> starQuestionId = userStarList.getRecords().stream()
                .map(UserStar::getQuestionId).collect(Collectors.toList());
        List<Question> starQuestionList = questionMapper.selectBatchIds(starQuestionId);
        Page<QuestionVo> resultPage = new Page<>(currentPage, size);
        resultPage.setTotal(userStarList.getTotal());
        List<QuestionVo> records = new ArrayList<>();
        if (!starQuestionList.isEmpty()) {
            records = starQuestionList.stream()
                    .map(question -> new QuestionVo().convertFromQuestion(question))
                    .collect(Collectors.toList());
        }
        resultPage.setRecords(records);
        return resultPage;
    }

    @Override
    public Boolean deleteStar(UserStarBo userStarBo) {
        Assert.notNull(userStarMapper.selectByQuestionId(userStarBo.getUserId(), userStarBo.getQuestionId()), "题目未收藏！");
        return userStarMapper.deleteStarQuestion(userStarBo) == 1;
    }
}
