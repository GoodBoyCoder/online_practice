package com.online.www.pojo.po.strategy.paper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.online.www.constant.QuestionTypeConstant;
import com.online.www.constant.SubjectIdConstant;
import com.online.www.mapper.QuestionMapper;
import com.online.www.pojo.po.Question;
import com.online.www.util.CollectionUtils;
import lombok.Getter;

/**
 * @author GoodBoyCoder
 * @date 2021-11-23
 */
public class PaperFormForSubjectFourStrategy implements PaperFormStrategy{
    @Getter
    private final QuestionMapper questionMapper;

    public static final Integer SUBJECT_ID = SubjectIdConstant.SUBJECT_FOUR;
    @Getter
    private final int JUDGE_NUMBER = 20;
    @Getter
    private final int SINGLE_NUMBER = 20;
    @Getter
    private final int Multi_NUMBER = 10;

    public PaperFormForSubjectFourStrategy(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Override
    public Map<Integer, List<Question>> autoCreate() {
        List<Question> questionSingleList = questionMapper.selectQuestionsBySubject(SUBJECT_ID, QuestionTypeConstant.SINGLE);
        List<Question> singleQuestion = CollectionUtils.getRandomIgnoreOrigin(questionSingleList, SINGLE_NUMBER);

        List<Question> questionJudgeList = questionMapper.selectQuestionsBySubject(SUBJECT_ID, QuestionTypeConstant.JUDGE);
        List<Question> judgeQuestion = CollectionUtils.getRandomIgnoreOrigin(questionJudgeList, JUDGE_NUMBER);

        List<Question> questionMultiList = questionMapper.selectQuestionsBySubject(SUBJECT_ID, QuestionTypeConstant.MULTIPLE);
        List<Question> multiQuestion = CollectionUtils.getRandomIgnoreOrigin(questionMultiList, Multi_NUMBER);

        Map<Integer, List<Question>> resultMap = new HashMap<>(4);
        resultMap.put(QuestionTypeConstant.JUDGE, judgeQuestion);
        resultMap.put(QuestionTypeConstant.SINGLE, singleQuestion);
        resultMap.put(QuestionTypeConstant.MULTIPLE, multiQuestion);
        return resultMap;
    }

    @Override
    public List<Question> createInOrderList() {
        List<Question> questionSingleList = questionMapper.selectQuestionsBySubject(SUBJECT_ID, QuestionTypeConstant.SINGLE);
        List<Question> resultList = new LinkedList<>(CollectionUtils.getRandomIgnoreOrigin(questionSingleList, SINGLE_NUMBER));

        List<Question> questionJudgeList = questionMapper.selectQuestionsBySubject(SUBJECT_ID, QuestionTypeConstant.JUDGE);
        resultList.addAll(CollectionUtils.getRandomIgnoreOrigin(questionJudgeList, JUDGE_NUMBER));

        List<Question> questionMultiList = questionMapper.selectQuestionsBySubject(SUBJECT_ID, QuestionTypeConstant.MULTIPLE);
        resultList.addAll(CollectionUtils.getRandomIgnoreOrigin(questionMultiList, Multi_NUMBER));
        return resultList;
    }
}
