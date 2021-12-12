package com.online.www.service.impl;

import javax.annotation.Resource;
import java.text.NumberFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.www.constant.QuestionTypeConstant;
import com.online.www.constant.SubjectIdConstant;
import com.online.www.mapper.QuestionMapper;
import com.online.www.mapper.SubjectMapper;
import com.online.www.mapper.UserQuestionMapper;
import com.online.www.mapper.UserStarMapper;
import com.online.www.pojo.po.Question;
import com.online.www.pojo.po.Subject;
import com.online.www.pojo.po.UserQuestion;
import com.online.www.pojo.po.UserStar;
import com.online.www.pojo.po.strategy.analysis.AnalysisStrategy;
import com.online.www.pojo.po.strategy.analysis.AnalysisSubjectFourStrategy;
import com.online.www.pojo.po.strategy.analysis.AnalysisSubjectOneStrategy;
import com.online.www.pojo.vo.AnalysisVo;
import com.online.www.pojo.vo.QuestionVo;
import com.online.www.service.UserQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    @Resource
    private UserQuestionMapper userQuestionMapper;
    @Resource
    private SubjectMapper subjectMapper;

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


    @Override
    public List<AnalysisVo> userQuestionAnalysis(Integer userId) {
        List<AnalysisVo> analysisVos = new ArrayList<>(2);
        // 查出所有做题数据
        List<UserQuestion> userQuestions = userQuestionMapper.selectByUser(userId);
        // 查询所有科目
        List<Subject> subjects = subjectMapper.selectAll();
        // 没有数据，提前结束
        if (userQuestions.size() <= 0) {
            for (Subject subject : subjects) {
                analysisVos.add(AnalysisVo.getNoDataAnalysisVo(subject));
            }
            return analysisVos;
        }
        // 根据题目Id分类
        Map<Long, List<UserQuestion>> userQuestionMap = userQuestions.stream().collect(Collectors.groupingBy(UserQuestion::getQuestionId));
        // 查出相关题目
        List<Question> questions = questionMapper.selectByIds(new ArrayList<>(userQuestionMap.keySet()));
        // 根据科目分类
        Map<Integer, List<Question>> subQuestionMap = questions.stream().collect(Collectors.groupingBy(Question::getSubjectId));

        for (Subject subject : subjects) {
            List<Question> questionList = subQuestionMap.getOrDefault(subject.getId(), Collections.emptyList());

            // 总正确率, 总通过题数, 总错误题数, 总题数
            Map<String, Number> totalCountMap = countUserQuestions(questionList, userQuestionMap);

            // 按题型分类
            Map<Object, List<Question>> questionTypeMap = questionList.stream().collect(Collectors.groupingBy(Question::getQuestionType));

            // 判断题正确率, 判断题通过题数, 判断题错误题数, 判断题题数
            List<Question> judgeQuestions = questionTypeMap.getOrDefault(QuestionTypeConstant.JUDGE, Collections.emptyList());
            Map<String, Number> judgeCountMap = countUserQuestions(judgeQuestions, userQuestionMap);
            double judgePassRate = (double) judgeCountMap.get("rate");
            // 单选题正确率, 单选题通过题数, 单选题错误题数, 单选题题数
            List<Question> singleQuestions = questionTypeMap.getOrDefault(QuestionTypeConstant.SINGLE, Collections.emptyList());
            Map<String, Number> singleCountMap = countUserQuestions(singleQuestions, userQuestionMap);
            double singlePassRate = (double) singleCountMap.get("rate");
            // 多选题正确率, 多选题通过题数, 多选题错误题数, 多选题题数
            List<Question> multipleQuestions = questionTypeMap.getOrDefault(QuestionTypeConstant.MULTIPLE, Collections.emptyList());
            Map<String, Number> multipleCountMap = countUserQuestions(multipleQuestions, userQuestionMap);
            double multiplePassRate = (double) multipleCountMap.get("rate");

            // 根据不同科目预测通过率
            AnalysisStrategy analysisStrategy;
            switch (subject.getId()) {
                case SubjectIdConstant.SUBJECT_ONE:
                    analysisStrategy = new AnalysisSubjectOneStrategy();
                    break;
                case SubjectIdConstant.SUBJECT_FOUR:
                    analysisStrategy = new AnalysisSubjectFourStrategy();
                    break;
                default:
                    throw new RuntimeException("无策略可用！");
            }
            double rate = analysisStrategy.analysis(judgePassRate, singlePassRate, multiplePassRate);

            AnalysisVo analysisVo = AnalysisVo.getInstance()
                    .setSubjectId(subject.getId())
                    .setSubjectName(subject.getSubjectName())
                    .setRate(rate)

                    .setTotalPassRate((double) totalCountMap.get("rate"))
                    .setTotalCount((int) totalCountMap.get("totalCount"))
                    .setTotalPassCount((int) totalCountMap.get("passCount"))
                    .setTotalErrorCount((int) totalCountMap.get("errorCount"))

                    .setJudgePassRate((double) judgeCountMap.get("rate"))
                    .setJudgeTotalCount((int) judgeCountMap.get("totalCount"))
                    .setJudgePassCount((int) judgeCountMap.get("passCount"))
                    .setJudgeErrorCount((int) judgeCountMap.get("errorCount"))

                    .setSinglePassRate((double) singleCountMap.get("rate"))
                    .setSingleTotalCount((int) singleCountMap.get("totalCount"))
                    .setSinglePassCount((int) singleCountMap.get("passCount"))
                    .setSingleErrorCount((int) singleCountMap.get("errorCount"))

                    .setMultiplePassRate((double) multipleCountMap.get("rate"))
                    .setMultipleTotalCount((int) multipleCountMap.get("totalCount"))
                    .setMultiplePassCount((int) multipleCountMap.get("passCount"))
                    .setMultipleErrorCount((int) multipleCountMap.get("errorCount"));

            analysisVos.add(analysisVo);
        }
        return analysisVos;
    }

    /**
     * 计算通过数，错误数，总数，通过率
     * @param questions 题目
     * @param userQuestionMap 题目对应做题集
     * @return 计算集Map
     */
    private Map<String, Number> countUserQuestions(List<Question> questions, Map<Long, List<UserQuestion>> userQuestionMap) {
        int passCount = 0, totalCount = 0;
        for (Question question : questions) {
            List<UserQuestion> userQuestionList = userQuestionMap.get(question.getId());
            totalCount += userQuestionList.size();
            passCount += (int) userQuestionList.stream().filter(UserQuestion::getCompleteTrue).count();
        }
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        double rate = (totalCount == 0)
                ? 0
                : Double.parseDouble(numberFormat.format(passCount * 1.0 / totalCount));
        Map<String, Number> map = new HashMap<>(3);

        map.put("passCount", passCount);
        map.put("errorCount", totalCount - passCount);
        map.put("totalCount", totalCount);
        map.put("rate", rate);
        return map;
    }
}
