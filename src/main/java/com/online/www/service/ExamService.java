package com.online.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.online.www.pojo.bo.CreateExamBo;
import com.online.www.pojo.bo.ExamCommitBo;
import com.online.www.pojo.po.Exam;
import com.online.www.pojo.vo.ExamWithQuestionVo;
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
public interface ExamService extends IService<Exam> {
    /**
     * 自动组卷
     *
     * @param createExamBo 组卷信息
     * @param userId       用户ID
     * @return 组卷结果
     */
    ExamWithQuestionVo autoCreateExam(CreateExamBo createExamBo, Integer userId);

    /**
     * 保存试卷信息
     * @param examCommitBo 试卷提交信息
     * @return 是否保存成功
     */
    boolean saveExam(ExamCommitBo examCommitBo);
}
