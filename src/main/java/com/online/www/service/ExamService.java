package com.online.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.online.www.pojo.bo.CreateExamBo;
import com.online.www.pojo.po.Exam;
import com.online.www.pojo.vo.ExamWithQuestionVo;

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
}
