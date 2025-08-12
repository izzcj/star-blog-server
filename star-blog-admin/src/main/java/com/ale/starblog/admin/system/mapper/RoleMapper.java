package com.ale.starblog.admin.system.mapper;

import com.ale.starblog.admin.system.domain.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色Mapper
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/31
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
