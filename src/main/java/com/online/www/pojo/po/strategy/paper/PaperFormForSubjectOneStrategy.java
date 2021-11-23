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
public class PaperFormForSubjectOneStrategy implements PaperFormStrategy {
    @Getter
    private final QuestionMapper questionMapper;

    public static final Integer SUBJECT_ID = SubjectIdConstant.SUBJECT_ONE;
    @Getter
    private final int JUDGE_NUMBER = 40;
    @Getter
    private final int SINGLE_NUMBER = 60;

    public PaperFormForSubjectOneStrategy(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Override
    public Map<Integer, List<Question>> autoCreate() {
        //策略为总题100道，包含40道判断和60道单选
        List<Question> questionJudgeList = questionMapper.selectQuestionsBySubject(SUBJECT_ID, QuestionTypeConstant.JUDGE);
        //随机抽取40道
        List<Question> judgeQuestion = CollectionUtils.getRandomIgnoreOrigin(questionJudgeList, JUDGE_NUMBER);

        //随机抽取60道单选
        List<Question> questionSingleList = questionMapper.selectQuestionsBySubject(SUBJECT_ID, QuestionTypeConstant.SINGLE);
        List<Question> singleQuestion = CollectionUtils.getRandomIgnoreOrigin(questionSingleList, SINGLE_NUMBER);

        Map<Integer, List<Question>> resultMap = new HashMap<>(2);
        resultMap.put(QuestionTypeConstant.JUDGE, judgeQuestion);
        resultMap.put(QuestionTypeConstant.SINGLE, singleQuestion);
        return resultMap;
    }

    @Override
    public List<Question> createInOrderList() {
        List<Question> questionJudgeList = questionMapper.selectQuestionsBySubject(SUBJECT_ID, QuestionTypeConstant.JUDGE);
        List<Question> resultList = new LinkedList<>(CollectionUtils.getRandomIgnoreOrigin(questionJudgeList, JUDGE_NUMBER));

        //随机抽取60道单选
        List<Question> questionSingleList = questionMapper.selectQuestionsBySubject(SUBJECT_ID, QuestionTypeConstant.SINGLE);
        resultList.addAll(CollectionUtils.getRandomIgnoreOrigin(questionSingleList, SINGLE_NUMBER));
        return resultList;
    }
}
