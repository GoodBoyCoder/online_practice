package com.online.www.service.impl;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.www.mapper.*;
import com.online.www.pojo.bo.CreateExamBo;
import com.online.www.pojo.bo.ExamCommitBo;
import com.online.www.pojo.bo.QuestionJudgeBo;
import com.online.www.pojo.po.*;
import com.online.www.pojo.vo.ExamVo;
import com.online.www.pojo.vo.ExamWithQuestionVo;
import com.online.www.pojo.vo.QuestionVo;
import com.online.www.service.ExamService;
import org.springframework.beans.BeanUtils;
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
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements ExamService {
    @Resource
    private SubjectMapper subjectMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private UserStarMapper userStarMapper;
    @Resource
    private ExamQuestionMapper examQuestionMapper;
    @Resource
    private ExamUserMapper examUserMapper;

    @Override
    public ExamWithQuestionVo autoCreateExam(CreateExamBo createExamBo, Integer userId) {
        //查询科目
        Subject subject = subjectMapper.selectById(createExamBo.getSubjectId());
        Assert.notNull(subject, "所选科目不存在");

        ExamWithQuestionVo examWithQuestionVo = new ExamWithQuestionVo();
        examWithQuestionVo.setExamName(StringUtils.isEmpty(createExamBo.getExamName()) ? "自动生成模拟考试" : createExamBo.getExamName());
        examWithQuestionVo.setDuration(createExamBo.getDuration());
        examWithQuestionVo.setStartTime(createExamBo.getStartTime());
        examWithQuestionVo.setSubject(subject.getSubjectName());

        List<Question> inOrderList = new Exam().setSubjectId(createExamBo.getSubjectId()).getPaperFormStrategy(questionMapper).createInOrderList();
        List<QuestionVo> collect = inOrderList.stream()
                .map(question -> new QuestionVo().convertFromQuestionWithNoAnswer(question))
                .collect(Collectors.toList());
        examWithQuestionVo.setQuestionList(collect);

        //检查收藏状态
        List<Long> questionIds = inOrderList.stream()
                .map(Question::getId)
                .collect(Collectors.toList());
        List<UserStar> userStars = userStarMapper.selectByQuestionIdList(userId, questionIds);
        Map<Long, UserStar> userStarMap = userStars.stream().collect(Collectors.toMap(UserStar::getQuestionId, Function.identity()));
        collect.forEach(record -> record.setStared(null != userStarMap.get(record.getId())));
        return examWithQuestionVo;
    }

    @Override
    public Exam saveExam(ExamCommitBo examCommitBo, Integer userId) {
        CreateExamBo createExamBo = examCommitBo.getCreateExamBo();
        List<QuestionJudgeBo> questionJudgeBoList = examCommitBo.getQuestionJudgeBoList();
        // 保存试卷
        Exam exam = new Exam();
        BeanUtils.copyProperties(createExamBo, exam);
        exam.setCreatTime(LocalDateTime.now());
        baseMapper.insert(exam);
        // 保存试题
        for (int i = 0; i < questionJudgeBoList.size(); i++) {
            ExamQuestion examQuestion = new ExamQuestion();
            examQuestion.setExamId(exam.getId());
            examQuestion.setNumber(i + 1);
            examQuestion.setQuestionId(questionJudgeBoList.get(i).getQuestionId());
            examQuestionMapper.insert(examQuestion);
        }
        // 保存考试用户关系
        ExamUser examUser = new ExamUser();
        examUser.setExamId(exam.getId());
        examUser.setUserId(userId);
        examUser.setTotalQuestion(questionJudgeBoList.size());
        examUserMapper.insert(examUser);
        return exam;
    }

    @Override
    public List<ExamVo> getHistoryExams(Integer userId) {
        // 考试-用户关系
        List<ExamUser> examUsers = examUserMapper.selectByUser(userId);

        return examUsers.stream()
                .map(examUser -> {
                    Exam exam = baseMapper.selectById(examUser.getExamId());
                    Subject subject = subjectMapper.selectById(exam.getSubjectId());
                    return new ExamVo(exam, examUser, subject.getSubjectName());
                })
                // 按时间降序
                .sorted(Comparator.comparing(ExamVo::getStartTime).reversed())
                .collect(Collectors.toList());
    }
}
