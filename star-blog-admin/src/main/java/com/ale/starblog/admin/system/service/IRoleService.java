package com.ale.starblog.admin.system.service;

import com.ale.starblog.admin.system.domain.entity.Role;
import com.ale.starblog.admin.system.domain.pojo.role.CreateRoleDTO;
import com.ale.starblog.admin.system.domain.pojo.role.ModifyRoleDTO;
import com.ale.starblog.admin.system.domain.pojo.role.RoleBO;
import com.ale.starblog.framework.core.service.ICrudService;

/**
 * 角色服务接口
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/31
 */
public interface IRoleService extends ICrudService<Role, RoleBO, CreateRoleDTO, ModifyRoleDTO> {
}
