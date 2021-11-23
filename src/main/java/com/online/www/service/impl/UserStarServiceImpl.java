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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        List<UserStar> userStarList = userStarMapper.selectStarQuestionByUserId(userId);
        List<Long> starQuestionId = new ArrayList<>();
        if (!userStarList.isEmpty()){
            for (UserStar us:userStarList) {
                starQuestionId.add(us.getQuestionId());
            }
        }
        List<Question> starQuestionList = questionMapper.selectBatchIds(starQuestionId);

        Page<QuestionVo> resultPage = new Page<>(currentPage, size);
        resultPage.setTotal(starQuestionList.size());
        List<Question> questionCollect = starQuestionList.stream()
                .skip((long) size * (currentPage - 1))
                .limit(size)
                .collect(Collectors.toList());
        List<QuestionVo> records = new ArrayList<>();
        if (!questionCollect.isEmpty()) {
            for (Question q : questionCollect) {
                records.add(new QuestionVo().convertFromQuestion(q));
            }
        }
        resultPage.setRecords(records);
        return resultPage;
    }

    @Override
    public Boolean deleteStar(UserStarBo userStarBo) {
        Assert.notNull(userStarMapper.selectByQuestionId(userStarBo.getUserId(), userStarBo.getQuestionId()), "题目未收藏！");
        Map<String,Object> map = new HashMap<>(2);
        map.put("user_id",userStarBo.getUserId());
        map.put("question_id",userStarBo.getQuestionId());
        return userStarMapper.deleteByMap(map) == 1;
    }
}
