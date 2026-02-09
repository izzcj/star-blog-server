package com.ale.starblog.admin.system.service;

import com.ale.starblog.admin.system.domain.entity.Role;
import com.ale.starblog.admin.system.domain.pojo.role.RoleBO;
import com.ale.starblog.admin.system.domain.pojo.role.RoleQuery;
import com.ale.starblog.admin.system.mapper.RoleMapper;
import com.ale.starblog.framework.core.service.AbstractCrudService;
import org.springframework.stereotype.Service;

/**
 * 角色Service实现类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/31
 */
@Service
public class RoleService extends AbstractCrudService<RoleMapper, Role, RoleBO, RoleQuery> {
}
