package com.online.www.service.impl;

import javax.annotation.Resource;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.www.mapper.ExamMapper;
import com.online.www.mapper.QuestionMapper;
import com.online.www.mapper.SubjectMapper;
import com.online.www.mapper.UserStarMapper;
import com.online.www.pojo.bo.CreateExamBo;
import com.online.www.pojo.po.Exam;
import com.online.www.pojo.po.Question;
import com.online.www.pojo.po.Subject;
import com.online.www.pojo.po.UserStar;
import com.online.www.pojo.vo.ExamWithQuestionVo;
import com.online.www.pojo.vo.QuestionVo;
import com.online.www.service.ExamService;
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
}
