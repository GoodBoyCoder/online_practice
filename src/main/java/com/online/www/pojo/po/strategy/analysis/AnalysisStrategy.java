package com.online.www.pojo.po.strategy.analysis;

/**
 * 个人学习情况分析接口
 * @author yellow
 *
 */
public interface AnalysisStrategy {

    /**
     * 根据平时学习情况预测通过率
     * 预测通过率 = 判断题正确率 * 对应科目判断题题数 + 单选题正确率 * 对应科目单选题题数 + 多选题正确率 * 对应科目多选题题数
     * @param judgePassRate 判断题正确率
     * @param singlePassRate 单选题正确率
     * @param multiplePassRate 多选题正确率
     * @return 预测通过率
     */
    double analysis(double judgePassRate,double singlePassRate,double multiplePassRate);

}