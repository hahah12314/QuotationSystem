package com.wanshu.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.wanshu.sys.entity.SysMenu;
import com.wanshu.sys.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author hehe
 * @since 2024-10-08
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<Long> queryMenuByRoleId(Long roleId);

    List<SysRole> queryRoleByMenuId(Long menuId);

    List<SysMenu> queryMenuRoleByRoleId(Long roleId);
}
