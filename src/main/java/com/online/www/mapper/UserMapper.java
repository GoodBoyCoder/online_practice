package com.online.www.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.online.www.pojo.po.User;
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
public interface UserMapper extends BaseMapper<User> {

}
