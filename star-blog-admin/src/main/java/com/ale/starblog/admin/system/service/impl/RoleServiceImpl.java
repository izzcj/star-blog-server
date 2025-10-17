package com.ale.starblog.admin.system.service.impl;

import com.ale.starblog.admin.system.domain.entity.Role;
import com.ale.starblog.admin.system.domain.pojo.role.CreateRoleDTO;
import com.ale.starblog.admin.system.domain.pojo.role.ModifyRoleDTO;
import com.ale.starblog.admin.system.domain.pojo.role.RoleBO;
import com.ale.starblog.admin.system.mapper.RoleMapper;
import com.ale.starblog.admin.system.service.IRoleService;
import com.ale.starblog.framework.core.service.AbstractCrudServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色Service实现类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/31
 */
@Service
public class RoleServiceImpl extends AbstractCrudServiceImpl<RoleMapper, Role, RoleBO, CreateRoleDTO, ModifyRoleDTO> implements IRoleService {

}
