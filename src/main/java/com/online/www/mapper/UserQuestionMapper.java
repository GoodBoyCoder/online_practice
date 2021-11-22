package com.online.www.mapper;

import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.online.www.pojo.po.UserQuestion;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author GoodBoyCoder
 * @date 2021-11-19
 */
@Mapper
public interface UserQuestionMapper extends BaseMapper<UserQuestion> {

    /**
     * 通过用户ID查询
     *
     * @param userId 用户ID
     * @return 做题详情集合
     */
    default List<UserQuestion> selectByUser(Integer userId) {
        return selectByUserAndStatus(userId, null);
    }

    /**
     * 根据用户ID和做题状况查询
     *
     * @param userId       用户ID
     * @param completeTrue 是否已做对
     * @return 做题详情集合
     */
    default List<UserQuestion> selectByUserAndStatus(Integer userId, Boolean completeTrue) {
        return selectByUserAndQuestionWithStatus(userId, null, completeTrue);
    }

    /**
     * 通过用户ID-题目ID查询
     *
     * @param userId       用户ID
     * @param questionId   题目ID
     * @param completeTrue 做题结果
     * @return 做题详情集合
     */
    default List<UserQuestion> selectByUserAndQuestionWithStatus(Integer userId, Long questionId, Boolean completeTrue) {
        QueryWrapper<UserQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(UserQuestion::getId, UserQuestion::getQuestionId, UserQuestion::getUserId,
                        UserQuestion::getAnswer, UserQuestion::getCompleteTrue, UserQuestion::getResult,
                        UserQuestion::getModifyTime)
                .eq(UserQuestion::getUserId, userId)
                .eq(Objects.nonNull(questionId), UserQuestion::getQuestionId, questionId)
                .eq(Objects.nonNull(completeTrue), UserQuestion::getCompleteTrue, completeTrue);
        return selectList(queryWrapper);
    }
}
