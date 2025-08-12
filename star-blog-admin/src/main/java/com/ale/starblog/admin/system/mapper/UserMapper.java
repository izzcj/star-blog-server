package com.ale.starblog.admin.system.mapper;

import com.ale.starblog.admin.system.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户mapper
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/13
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
