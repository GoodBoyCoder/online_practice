package com.online.www.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.online.www.pojo.po.UserStar;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Lenovo
 */
@Mapper
public interface UserStarMapper extends BaseMapper<UserStar> {
    /**
     * 收藏题目
     * @param userId 用户ID
     * @param questionId 问题ID
     * @return 插入条数
     */
    default UserStar selectByQuestionId(Integer userId,Long questionId){
        QueryWrapper<UserStar> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(UserStar::getQuestionId, UserStar::getUserId)
                .eq(UserStar::getUserId, userId)
                .eq(UserStar::getQuestionId, questionId);
        return selectOne(queryWrapper);
    };


}
