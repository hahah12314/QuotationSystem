package com.wanshu.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanshu.common.util.PageUtils;
import com.wanshu.sys.dto.SysRoleQueryDto;
import com.wanshu.sys.entity.SysRole;


import java.util.List;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author hehe
 * @since 2024-10-08
 */
public interface ISysRoleService extends IService<SysRole> {

    List<SysRole> queryRoleByUserId(Long userId);

    PageUtils queryPageRole(SysRoleQueryDto sysRoleQueryDto);

    boolean updateRole(SysRole sysRole);

    boolean saveRole(SysRole sysRole);

    String checkRoleName(String roleName);

    String deleteRole(Long roleId);
}
