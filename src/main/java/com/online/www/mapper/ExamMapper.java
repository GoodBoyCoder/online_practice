package com.online.www.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.online.www.pojo.po.Exam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author online
 * @since 2021-11-19
 */
@Mapper
public interface ExamMapper extends BaseMapper<Exam> {

    /**
     * 根据考试id批量查询
     *
     * @param examIdList 考试id集合
     * @return 考试信息集合
     */
    default List<Exam> selectInExamIdList(List<Integer> examIdList) {
        QueryWrapper<Exam> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(Exam::getId, Exam::getSubjectId)
                .in(Exam::getId, examIdList);
        return selectList(queryWrapper);
    }
}
