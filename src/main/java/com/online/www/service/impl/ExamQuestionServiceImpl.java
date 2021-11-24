package com.online.www.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.www.mapper.*;
import com.online.www.pojo.bo.QuestionJudgeBo;
import com.online.www.pojo.po.*;
import com.online.www.pojo.vo.QuestionJudgeDetailVo;
import com.online.www.pojo.vo.QuestionJudgeVo;
import com.online.www.pojo.vo.QuestionVo;
import com.online.www.service.ExamQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author online
 * @since 2021-11-19
 */
@Service
public class ExamQuestionServiceImpl extends ServiceImpl<ExamQuestionMapper, ExamQuestion> implements ExamQuestionService {

    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private UserQuestionMapper userQuestionMapper;
    @Resource
    private ExamUserMapper examUserMapper;

    @Override
    public List<QuestionJudgeVo> examQuestionJudge(List<QuestionJudgeBo> questionJudgeBoList, Integer userId, Integer examId) {
        ExamUser examUser = examUserMapper.selectByUserAndExam(userId, examId);
        Assert.notNull(examUser, "考试不存在");
        // 判题
        List<QuestionJudgeDetailVo> questionJudgeDetailVos = questionJudgeBoList.stream()
                .map(questionJudgeBo -> {
                    questionJudgeBo.setUserId(userId);
                    QuestionJudgeDetailVo examQuestionJudgeDetail = this.getExamQuestionJudgeDetail(questionJudgeBo);
                    QuestionVo questionVo = examQuestionJudgeDetail.getQuestionVo();
                    QuestionJudgeVo questionJudgeVo = examQuestionJudgeDetail.getQuestionJudgeVo();
                    // 保存结果
                    UserQuestion userQuestion = new UserQuestion();
                    userQuestion.setQuestionId(questionVo.getId());
                    userQuestion.setUserId(userId);
                    userQuestion.setAnswer(questionJudgeVo.getAnswer());
                    userQuestion.setCompleteTrue(questionJudgeVo.getCorrect());
                    userQuestion.setModifyTime(LocalDateTime.now());
                    userQuestion.setJudgeExamId(examUser.getId());
                    userQuestionMapper.insert(userQuestion);
                    return examQuestionJudgeDetail;
                })
                .collect(Collectors.toList());
        // 通过的
        List<QuestionJudgeDetailVo> passDetailVos = questionJudgeDetailVos.stream()
                .filter(questionJudgeDetailVo -> questionJudgeDetailVo.getQuestionJudgeVo().getCorrect())
                .collect(Collectors.toList());
        // 总分
        double totalScore = passDetailVos.stream()
                .mapToDouble(questionJudgeDetailVo -> questionJudgeDetailVo.getQuestionVo().getMark())
                .sum();
        // 更新
        examUser.setPassQuestion(passDetailVos.size());
        examUser.setTotalScore(totalScore);
        examUserMapper.updateById(examUser);

        return questionJudgeDetailVos.stream()
                .map(QuestionJudgeDetailVo::getQuestionJudgeVo)
                .collect(Collectors.toList());
    }

    private QuestionJudgeDetailVo getExamQuestionJudgeDetail(QuestionJudgeBo judgeBo) {
        Question question = questionMapper.selectById(judgeBo.getQuestionId());
        Assert.notNull(question, "题目不存在！");

        QuestionJudgeVo questionJudgeVo = new QuestionJudgeVo();
        questionJudgeVo.setQuestionId(question.getId());
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
        }
        return new QuestionJudgeDetailVo(new QuestionVo().convertFromQuestion(question), questionJudgeVo);
    }
}
