package com.online.www.pojo.po.strategy.analysis;

import com.online.www.constant.SubjectIdConstant;
import lombok.Getter;

/**
 * 预测科目四的通过率
 * @author yellow
 */
public class AnalysisSubjectFourStrategy implements AnalysisStrategy{
    public static final Integer SUBJECT_ID = SubjectIdConstant.SUBJECT_FOUR;
    @Getter
    private final int JUDGE_NUMBER = 20;
    @Getter
    private final int SINGLE_NUMBER = 20;
    @Getter
    private final int Multi_NUMBER = 10;

    @Override
    public double analysis(double judgePassRate, double singlePassRate, double multiplePassRate) {
        return (judgePassRate * JUDGE_NUMBER + singlePassRate * SINGLE_NUMBER + multiplePassRate * Multi_NUMBER)
                / (JUDGE_NUMBER + SINGLE_NUMBER + Multi_NUMBER);
    }

    public AnalysisSubjectFourStrategy() {
    }
}