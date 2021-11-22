package com.online.www.service.impl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.www.mapper.QuestionMapper;
import com.online.www.mapper.UserQuestionMapper;
import com.online.www.mapper.UserStarMapper;
import com.online.www.pojo.po.Question;
import com.online.www.pojo.po.UserQuestion;
import com.online.www.pojo.po.UserStar;
import com.online.www.pojo.vo.QuestionVo;
import com.online.www.service.UserQuestionService;
import org.springframework.stereotype.Service;

/**
 * @author GoodBoyCoder
 * @date 2021-11-19
 */
@Service
public class UserQuestionServiceImpl extends ServiceImpl<UserQuestionMapper, UserQuestion> implements UserQuestionService {
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private UserStarMapper userStarMapper;

    @Override
    public Page<QuestionVo> getErrorQuestion(Integer userId, Integer currentPage, Integer size) {
        Page<UserQuestion> userQuestionPage = baseMapper.selectQuestionPage(userId, false, currentPage, size);
        Page<QuestionVo> resultPage = new Page<>(currentPage, size);
        resultPage.setTotal(userQuestionPage.getTotal());

        List<QuestionVo> records = new ArrayList<>();
        if (!userQuestionPage.getRecords().isEmpty()) {
            //转换记录
            List<Long> questionIds = userQuestionPage.getRecords().stream()
                    .map(UserQuestion::getQuestionId)
                    .distinct()
                    .collect(Collectors.toList());
            List<Question> questions = questionMapper.selectBatchIds(questionIds);
            records = questions.stream()
                    .map(question -> new QuestionVo().convertFromQuestionWithNoAnswer(question))
                    .collect(Collectors.toList());

            //检查收藏
            List<UserStar> userStars = userStarMapper.selectByQuestionIdList(userId, questionIds);
            Map<Long, UserStar> userStarMap = userStars.stream()
                    .collect(Collectors.toMap(UserStar::getQuestionId, Function.identity()));
            records.forEach(record -> record.setStared(null != userStarMap.get(record.getId())));
        }

        resultPage.setRecords(records);
        return resultPage;
    }
}
