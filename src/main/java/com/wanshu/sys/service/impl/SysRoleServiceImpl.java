package com.wanshu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wanshu.common.annotation.SystemLog;
import com.wanshu.common.util.PageUtils;
import com.wanshu.sys.dto.SysRoleQueryDto;
import com.wanshu.sys.entity.SysRole;
import com.wanshu.sys.entity.SysUser;
import com.wanshu.sys.mapper.SysRoleMapper;
import com.wanshu.sys.mapper.SysUserMapper;
import com.wanshu.sys.service.ISysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author hehe
 * @since 2024-10-08
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public List<SysRole> queryRoleByUserId(Long userId) {
        return sysRoleMapper.queryRoleByUserId(userId);
    }

    @Override
    public PageUtils queryPageRole(SysRoleQueryDto sysRoleQueryDto) {
        QueryWrapper<SysRole> wrapper=  new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(sysRoleQueryDto.getRoleName()), "role_name", sysRoleQueryDto.getRoleName());
        Page<SysRole> page = this.page(sysRoleQueryDto.page(),wrapper);
        return new PageUtils(page);
    }

    @Override
    @Transactional
    @SystemLog(value = "更新角色")
    public boolean updateRole(SysRole sysRole) {
        sysRole.setUpdateTime(LocalDateTime.now());
        this.updateById(sysRole);
        if(sysRole.getMenuList()!=null ) {
            sysRoleMapper.deleteMenuByRoleId(sysRole.getRoleId());
            sysRoleMapper.insertMenuByRoleId(sysRole.getMenuList(), sysRole.getRoleId());
        }
        return true;

    }

    @Override
    @SystemLog(value = "新增角色")
    public boolean saveRole(SysRole sysRole) {
        sysRole.setCreateTime(LocalDateTime.now());
        sysRole.setUpdateTime(LocalDateTime.now());
        this.baseMapper.insert(sysRole);
        if(sysRole.getMenuList()!=null){
            sysRoleMapper.insertMenuByRoleId(sysRole.getMenuList(), sysRole.getRoleId());
        }

        return true;

    }

    @Override

    public String checkRoleName(String roleName) {
        int count= this.count(new QueryWrapper<SysRole>().eq(StringUtils.isNotEmpty(roleName),"role_name", roleName));
        if(count>0){
            return "fail";
        }
        return "success";
    }

    @Override
    @Transactional
    @SystemLog(value = "删除角色")
    public String deleteRole(Long roleId) {
        List<SysUser> sysUsers = sysRoleMapper.queryUserByRoleId(roleId);
        if(sysUsers!=null&&sysUsers.size()>0){
            return "fail";
        }
        this.sysRoleMapper.deleteMenuByRoleId(roleId);
        QueryWrapper<SysRole> wrapper= new QueryWrapper<>();
        wrapper.eq("role_id",roleId);
        this.sysRoleMapper.delete(wrapper);
        return "success";
    }
}
