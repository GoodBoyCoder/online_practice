package com.online.www.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.online.www.pojo.po.ExamUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author online
 * @since 2021-11-19
 */
@Mapper
public interface ExamUserMapper extends BaseMapper<ExamUser> {

    /**
     * 通过用户、考试查询
     * @param userId 用户ID
     * @param examId 考试ID
     * @return 考试-用户关系
     */
    default ExamUser selectByUserAndExam(Integer userId, Integer examId) {
        QueryWrapper<ExamUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(ExamUser::getId, ExamUser::getExamId, ExamUser::getUserId, ExamUser::getTotalQuestion)
                .eq(Objects.nonNull(userId), ExamUser::getUserId, userId)
                .eq(Objects.nonNull(examId), ExamUser::getExamId, examId);
        return selectOne(queryWrapper);
    }

    /**
     * 通过用户查询
     * @param userId 用户ID
     * @return 考试-用户关系列表
     */
    default List<ExamUser> selectByUser(Integer userId) {
        QueryWrapper<ExamUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(
                        ExamUser::getId, ExamUser::getExamId, ExamUser::getUserId, ExamUser::getTotalQuestion,
                        ExamUser::getPassQuestion, ExamUser::getTotalScore
                )
                .eq(Objects.nonNull(userId), ExamUser::getUserId, userId);
        return selectList(queryWrapper);
    }
}
