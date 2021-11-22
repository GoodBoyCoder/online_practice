package com.online.www.service.impl;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.www.mapper.QuestionMapper;
import com.online.www.mapper.UserQuestionMapper;
import com.online.www.mapper.UserStarMapper;
import com.online.www.pojo.bo.QuestionJudgeBo;
import com.online.www.pojo.bo.QuestionSelectBo;
import com.online.www.pojo.po.Question;
import com.online.www.pojo.po.UserQuestion;
import com.online.www.pojo.po.UserStar;
import com.online.www.pojo.vo.QuestionJudgeVo;
import com.online.www.pojo.vo.QuestionVo;
import com.online.www.service.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author online
 * @since 2021-11-19
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
    @Resource
    private UserQuestionMapper userQuestionMapper;
    @Resource
    private UserStarMapper userStarMapper;

    @Override
    public QuestionVo getRandomQuestion(QuestionSelectBo selectBo, Integer userId) {
        //查询用户做过并且正确的题目
        List<Long> questionDoneList = new ArrayList<>();
        if (selectBo.getSkipRight()) {
            List<UserQuestion> userQuestions = userQuestionMapper.selectByUserAndStatus(userId, true);
            questionDoneList.addAll(
                    userQuestions.stream()
                            .map(UserQuestion::getQuestionId)
                            .distinct()
                            .collect(Collectors.toList()));
        }

        //查询满足条件的随机题目
        //TODO 待优化(SQL方式查询/缓存？)
        List<Question> questionList = baseMapper.selectQuestions(selectBo, questionDoneList);
        if (!CollectionUtils.isEmpty(questionList)) {
            Question question = questionList.get((int) (Math.random() * questionList.size()));
            QuestionVo questionVo = new QuestionVo();
            questionVo.convertFromQuestionWithNoAnswer(question);

            //检查是否已经收藏
            UserStar userStar = userStarMapper.selectByQuestionId(userId, question.getId());
            questionVo.setStared(Objects.nonNull(userStar));
            return questionVo;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuestionJudgeVo getQuestionJudge(QuestionJudgeBo judgeBo) {
        Question question = baseMapper.selectById(judgeBo.getQuestionId());
        Assert.notNull(question, "题目不存在！");

        QuestionJudgeVo questionJudgeVo = new QuestionJudgeVo();
        questionJudgeVo.setAnswer(question.getAnswer());
        questionJudgeVo.setExplain(question.getQuestionExplain());

        //判题
        List<String> answerList = judgeBo.getAnswerList();
        if (CollectionUtils.isEmpty(answerList)) {
            questionJudgeVo.setCorrect(false);
        } else {
            String reply = answerList.stream()
                    .filter(s -> !StringUtils.isEmpty(s))
                    .map(String::trim)
                    .map(an -> an.substring(0, 1))
                    .collect(Collectors.joining());
            questionJudgeVo.setCorrect(question.getStrategy().judge(question.getAnswer(), reply));

            //保存做题记录
            UserQuestion userQuestion = new UserQuestion();
            userQuestion.setQuestionId(question.getId());
            userQuestion.setUserId(judgeBo.getUserId());
            userQuestion.setAnswer(reply);
            userQuestion.setCompleteTrue(questionJudgeVo.getCorrect());
            userQuestion.setModifyTime(LocalDateTime.now());
            if (questionJudgeVo.getCorrect()) {
                //正确的题目如果已经做了需要做更新
                List<UserQuestion> userQuestions = userQuestionMapper.selectByUserAndQuestionWithStatus(judgeBo.getUserId(),
                        judgeBo.getQuestionId(), true);
                if (CollectionUtils.isEmpty(userQuestions)) {
                    //第一次做
                    userQuestionMapper.insert(userQuestion);
                } else {
                    UserQuestion userQuestionRight = userQuestions.get(0);
                    userQuestionRight.setAnswer(reply);
                    userQuestionRight.setModifyTime(LocalDateTime.now());
                    userQuestionMapper.updateById(userQuestionRight);
                }
            } else {
                userQuestionMapper.insert(userQuestion);
            }
        }

        return questionJudgeVo;
    }
}
