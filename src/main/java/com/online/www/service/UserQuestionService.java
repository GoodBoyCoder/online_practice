package com.online.www.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.www.pojo.po.UserQuestion;
import com.online.www.pojo.vo.AnalysisVo;
import com.online.www.pojo.vo.QuestionVo;

/**
 * @author GoodBoy
 * @date 2021-11-19
 */
public interface UserQuestionService extends IService<UserQuestion> {
    /**
     * 分页获取错题
     *
     * @param userId      用户ID
     * @param currentPage 当前页
     * @param size        页大小
     * @return Page<QuestionVo>
     */
    Page<QuestionVo> getErrorQuestion(Integer userId, Integer currentPage, Integer size);

    /**
     * 考试分析：
     * 各科的
     * 完成的题目数
     * 判断题的正确率
     * 单选题的正确率
     * 多选题的正确率
     * 预测通过率
     * @param userId 用户id
     * @return 分析结果集合
     */
    List<AnalysisVo> userQuestionAnalysis(Integer userId);
}
