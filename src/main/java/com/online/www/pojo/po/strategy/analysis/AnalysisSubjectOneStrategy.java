package com.online.www.pojo.po.strategy.analysis;

import com.online.www.constant.SubjectIdConstant;
import lombok.Getter;

/**
 * 科目一预测通过率
 * @author  yellow
 *
 */
public class AnalysisSubjectOneStrategy implements AnalysisStrategy{
    public static final Integer SUBJECT_ID = SubjectIdConstant.SUBJECT_ONE;
    @Getter
    private final int JUDGE_NUMBER = 40;
    @Getter
    private final int SINGLE_NUMBER = 60;

    @Override
    public double analysis(double judgePassRate, double singlePassRate, double multiplePassRate) {
        return (judgePassRate * JUDGE_NUMBER + singlePassRate * SINGLE_NUMBER) / (JUDGE_NUMBER + SINGLE_NUMBER);
    }

    public AnalysisSubjectOneStrategy() {
    }
}