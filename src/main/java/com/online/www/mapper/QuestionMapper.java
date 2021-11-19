package com.online.www.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.online.www.pojo.po.Question;
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
public interface QuestionMapper extends BaseMapper<Question> {

}
