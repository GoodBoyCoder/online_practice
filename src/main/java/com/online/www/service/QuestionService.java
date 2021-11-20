package com.online.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.online.www.pojo.bo.QuestionSelectBo;
import com.online.www.pojo.po.Question;
import com.online.www.pojo.vo.QuestionVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author online
 * @since 2021-11-19
 */
public interface QuestionService extends IService<Question> {
    /**
     * 根据查询条件获取题目
     *
     * @param selectBo 查询条件
     * @param userId   用户ID
     * @return 符合条件的试题
     */
    QuestionVo getRandomQuestion(QuestionSelectBo selectBo, Integer userId);
}
