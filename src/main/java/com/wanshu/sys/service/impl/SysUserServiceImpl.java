package com.wanshu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wanshu.common.annotation.SystemLog;
import com.wanshu.common.util.PageUtils;
import com.wanshu.sys.dto.SysUserQueryDto;
import com.wanshu.sys.entity.SysRole;
import com.wanshu.sys.entity.SysUser;
import com.wanshu.sys.mapper.SysRoleMapper;
import com.wanshu.sys.mapper.SysUserMapper;
import com.wanshu.sys.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author hehe
 * @since 2024-10-08
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Override
    public List<SysUser> queryByUsername(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(username), "username", username);
        queryWrapper.eq("status", 1);
        return this.list(queryWrapper);
    }

    @Override
    public PageUtils queryPageUser(SysUserQueryDto sysUserQueryDto) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(sysUserQueryDto.getUsername()), "username", sysUserQueryDto.getUsername());
        Page<SysUser> page = this.page(sysUserQueryDto.page(), wrapper);
        return new PageUtils(page);
    }

    @Override
    @Transactional
    @SystemLog(value = "更新用户")
    public boolean updateUser(SysUser sysUser) {
        sysUser.setUpdateTime(LocalDateTime.now());
        if (sysUser.getPassword() != null) {
            sysUser.setPassword(new BCryptPasswordEncoder().encode(sysUser.getPassword().toString()));

        }
        this.updateById(sysUser);
        if (sysUser.getRoleList() != null) {
            userMapper.deleteRoleByUserId(sysUser.getUserId());
            userMapper.insertRoleByUserId(sysUser.getRoleList(), sysUser.getUserId());
        }
        return true;
    }

    @Override
    @SystemLog(value = "新增用户")
    public boolean saveUser(SysUser sysUser) {
        sysUser.setCreateTime(LocalDateTime.now());
        sysUser.setUpdateTime(LocalDateTime.now());
        sysUser.setPassword(new BCryptPasswordEncoder().encode(sysUser.getPassword().toString()));

        this.baseMapper.insert(sysUser);
        if (sysUser.getRoleList() != null) {
            userMapper.insertRoleByUserId(sysUser.getRoleList(), sysUser.getUserId());
        }

        return true;

    }

    @Override
    public String checkUsername(String username) {
        int count = this.count(new QueryWrapper<SysUser>().eq(StringUtils.isNotEmpty(username), "username", username));
        if (count > 0) {
            return "fail";
        }
        return "success";
    }

    @Override
    @Transactional
    public String deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            userMapper.deleteRoleByUserId(id);
        }
        userMapper.deleteBatchIds(ids);
        return "success";
    }

    @Override
    public SysUser getNowUser() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        String username = (String) authentication.getPrincipal();
        Long userId = userMapper.getUserIdByUserName(username);

        SysUser user=userMapper.selectById(userId);
        List<SysRole> roleList=roleMapper.queryRoleByUserId(userId);
        if(roleList.size()>0){
            user.setRoleName(roleList.get(0).getRoleName());
            user.setRoleId(roleList.get(0).getRoleId());
        }

        return user;

    }

    @Override
    public boolean validatePassword(String username, String password) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        SysUser user = userMapper.selectOne(queryWrapper);
        return new BCryptPasswordEncoder().matches(password, user.getPassword());

    }


}
