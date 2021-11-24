package com.online.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.online.www.pojo.bo.QuestionJudgeBo;
import com.online.www.pojo.po.ExamQuestion;
import com.online.www.pojo.vo.QuestionJudgeVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author online
 * @since 2021-11-19
 */
public interface ExamQuestionService extends IService<ExamQuestion> {

    /**
     * 获取考试作答结果列表
     * @param questionJudgeBoList 答题情况列表
     * @param userId 用户ID
     * @param examId 考试ID
     * @return 判断结果列表
     */
    Double examQuestionJudge(List<QuestionJudgeBo> questionJudgeBoList, Integer userId, Integer examId);

}
