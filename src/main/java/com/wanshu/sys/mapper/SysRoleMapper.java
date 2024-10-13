package com.wanshu.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.wanshu.sys.entity.SysRole;
import com.wanshu.sys.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author hehe
 * @since 2024-10-08
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> queryRoleByUserId(@Param("userId") Long userId);

    void deleteMenuByRoleId(Long roleId);

    void insertMenuByRoleId(@Param("menuList") List<Long> menuList, @Param("roleId") Long roleId);

    List<SysUser> queryUserByRoleId(Long roleId);
}
