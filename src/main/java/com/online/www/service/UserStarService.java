package com.online.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.online.www.pojo.bo.QuestionSelectBo;
import com.online.www.pojo.bo.UserStarBo;
import com.online.www.pojo.po.Question;
import com.online.www.pojo.po.UserStar;
import com.online.www.pojo.vo.QuestionVo;

/**
 * @author Lenovo
 */
public interface UserStarService extends IService<UserStar> {
    /**
     * 收藏题目
     * @param userStarBo userStarBo
     * @return 是否收藏成功
     */
    Boolean collectionQuestion(UserStarBo userStarBo);

    /**
     * 查询收藏题目
     * @param userId 用户ID
     * @return QuestionVo
     */
    QuestionVo  selectCollection(Integer userId);
}
