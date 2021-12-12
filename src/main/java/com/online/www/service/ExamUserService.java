package com.online.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.online.www.pojo.po.ExamUser;
import com.online.www.pojo.vo.ExamAnalysisVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author online
 * @since 2021-11-19
 */
public interface ExamUserService extends IService<ExamUser> {

    /**
     * 考试分析
     *
     * @param userId 用户id
     * @return 分析结果集合（各科的通过率）
     */
    List<ExamAnalysisVo> analysisExam(Integer userId);
}
