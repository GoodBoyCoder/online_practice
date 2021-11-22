package com.online.www.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
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
     *
     * @param userStarBo userStarBo
     * @return 是否收藏成功
     */
    Boolean collectionQuestion(UserStarBo userStarBo);

    /**
     * 查询收藏题目
     *
     * @param userId 用户ID
     * @return Boolean
     */
    boolean  selectCollection(Integer userId);

    /**
     * 分页获取收藏题目
     *
     * @param userId      用户ID
     * @param currentPage 当前页
     * @param size        页大小
     * @return Page<QuestionVo>
     */
    Page<QuestionVo> getStarQuestion(Integer userId, Integer currentPage, Integer size);
}
