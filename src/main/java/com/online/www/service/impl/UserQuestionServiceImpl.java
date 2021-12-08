package com.online.www.service.impl;

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
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        List<UserQuestion> userQuestionList = baseMapper.selectListByUserId(userId);
        //筛选出每道题的最新答题结果
        Map<Long, UserQuestion> userQuestionMap = userQuestionList.stream()
                .collect(Collectors.toMap(UserQuestion::getQuestionId, Function.identity(),
                        (c1, c2) -> c1.getModifyTime().isAfter(c2.getModifyTime()) ? c1 : c2));
        userQuestionMap.entrySet().removeIf(key -> key.getValue().getCompleteTrue());
        Collection<UserQuestion> userQuestionWithWrong = userQuestionMap.values();
        //手动分页
        List<UserQuestion> questionCollect = userQuestionWithWrong.stream()
                .sorted(Comparator.comparing(UserQuestion::getModifyTime))
                .skip((long) size * (currentPage - 1))
                .limit(size)
                .collect(Collectors.toList());

        Page<QuestionVo> resultPage = new Page<>(currentPage, size);
        resultPage.setTotal(userQuestionWithWrong.size());

        List<QuestionVo> records = new ArrayList<>();
        if (!userQuestionWithWrong.isEmpty()) {
            //转换记录
            List<Long> questionIds = questionCollect.stream()
                    .map(UserQuestion::getQuestionId)
                    .distinct()
                    .collect(Collectors.toList());
            List<Question> questions = questionMapper.selectBatchIds(questionIds);
            Map<Long, Question> questionMap = questions.stream().collect(Collectors.toMap(Question::getId, Function.identity()));

            records = questionCollect.stream()
                    .map(userQuestion -> {
                        Question question = questionMap.get(userQuestion.getQuestionId());
                        QuestionVo questionVo = new QuestionVo();
                        questionVo.convertFromQuestion(question);
                        if (CollectionUtils.isEmpty(questionVo.getOption())) {
                            questionVo.setMyAnswer(Collections.singletonList(userQuestion.getAnswer()));
                        } else {
                            List<String> myAnswers = questionVo.getOption().stream()
                                    .map(String::trim)
                                    .filter(option -> userQuestion.getAnswer().indexOf(option.charAt(0)) >= 0)
                                    .collect(Collectors.toList());
                            questionVo.setMyAnswer(myAnswers);
                        }
                        return questionVo;
                    }).collect(Collectors.toList());

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
