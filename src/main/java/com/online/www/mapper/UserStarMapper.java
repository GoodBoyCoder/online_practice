package com.online.www.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
     * @return 收藏集合
     */
    default List<UserStar> selectStarQuestionByUserId(Integer userId) {
        QueryWrapper<UserStar> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(UserStar::getQuestionId)
                .eq(UserStar::getUserId, userId);
        return selectList(queryWrapper);
    }

    /**
     * 分页获取收藏题目
     *
     * @param userId      用户ID
     * @param currentPage 当前页
     * @param size        页大小
     * @return Page<UserStar>
     */
    default Page<UserStar> selectCollectionPage(Integer userId, Integer currentPage, Integer size) {
        QueryWrapper<UserStar> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(UserStar::getId, UserStar::getQuestionId, UserStar::getUserId)
                .eq(UserStar::getUserId, userId)
                .orderByAsc(UserStar::getId);
        return selectPage(new Page<>(currentPage, size), queryWrapper);
    }


}
