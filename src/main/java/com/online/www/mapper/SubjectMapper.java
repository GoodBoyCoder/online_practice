package com.online.www.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.online.www.pojo.po.Subject;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author online
 * @since 2021-11-19
 */
@Mapper
public interface SubjectMapper extends BaseMapper<Subject> {
    /**
     * 查询所有科目
     * @return List<Subject>
     */
    default List<Subject> selectAll(){
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(Subject::getId, Subject::getSubjectName, Subject::getRecord);
        return selectList(queryWrapper);
    }
}
