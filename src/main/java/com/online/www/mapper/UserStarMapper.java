package com.online.www.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.www.pojo.bo.UserStarBo;
import com.online.www.pojo.po.Question;
import com.online.www.pojo.po.UserStar;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Lenovo
 */
@Mapper
public interface UserStarMapper extends BaseMapper<UserStar> {
    /**
     * 收藏题目
     *
     * @param userId     用户ID
     * @param questionId 问题ID
     * @return UserStar
     */
    default UserStar selectByQuestionId(Integer userId, Long questionId) {
        QueryWrapper<UserStar> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(UserStar::getQuestionId, UserStar::getUserId)
                .eq(UserStar::getUserId, userId)
                .eq(UserStar::getQuestionId, questionId);
        return selectOne(queryWrapper);
    }

    /**
     * 收藏题目
     *
     * @param userId         用户ID
     * @param questionIdList 问题ID集合
     * @return 收藏集合
     */
    default List<UserStar> selectByQuestionIdList(Integer userId, List<Long> questionIdList) {
        QueryWrapper<UserStar> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(UserStar::getQuestionId, UserStar::getUserId)
                .eq(UserStar::getUserId, userId)
                .in(UserStar::getQuestionId, questionIdList);
        return selectList(queryWrapper);
    }

    /**
     * 查询收藏题目集合
     *
     * @param userId 用户ID
     * @param currentPage 当前页
     * @param size 页大小
     * @return 收藏集合
     */
    default Page<UserStar> selectStarQuestionPageByUserId(Integer userId, Integer currentPage, Integer size) {
        QueryWrapper<UserStar> queryWrapper = new QueryWrapper<>();
        Page<UserStar> resultPage = new Page<>(currentPage,size);
        queryWrapper.lambda()
                .select(UserStar::getQuestionId, UserStar::getUserId)
                .eq(UserStar::getUserId, userId);
        return selectPage(resultPage,queryWrapper);
    }

    /**
     * 删除收藏题目
     *
     * @param userStarBo userStarBo
     * @return 删除条数
     */
    default int deleteStarQuestion(UserStarBo userStarBo) {
        QueryWrapper<UserStar> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(UserStar::getQuestionId, UserStar::getUserId)
                .eq(UserStar::getUserId, userStarBo.getUserId())
                .eq(UserStar::getQuestionId, userStarBo.getQuestionId());
        return delete(queryWrapper);
    }

}
