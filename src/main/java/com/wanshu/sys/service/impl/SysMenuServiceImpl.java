package com.wanshu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wanshu.common.annotation.SystemLog;
import com.wanshu.common.util.PageUtils;
import com.wanshu.sys.dto.SysMenuQueryDto;
import com.wanshu.sys.entity.SysMenu;
import com.wanshu.sys.entity.SysRole;
import com.wanshu.sys.mapper.SysMenuMapper;
import com.wanshu.sys.mapper.SysRoleMapper;
import com.wanshu.sys.mapper.SysUserMapper;
import com.wanshu.sys.service.ISysMenuService;
import com.wanshu.sys.vo.SysMenuVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author hehe
 * @since 2024-10-08
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    public List<SysMenu> getParentMenu(QueryWrapper <SysMenu> wrapper){
        List<SysMenu> parentMenus = sysMenuMapper.selectList(wrapper);
        for (SysMenu parentMenu : parentMenus) {
            QueryWrapper<SysMenu> childWrapper = new QueryWrapper<>();
            childWrapper.eq("parent_id",parentMenu.getMenuId());
            List<SysMenu> childMenus = sysMenuMapper.selectList(childWrapper);
            parentMenu.setChildren(childMenus);

        }
        return parentMenus;
    }
    @Override
    public SysMenuVo queryMenuAll() {

        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",0);
        List<SysMenu> parentMenus = getParentMenu(wrapper);
        System.out.println(parentMenus);
        return new SysMenuVo(parentMenus,null);
    }

    @Override
    public List<Long> queryMenuByRoleId(Long roleId) {
        return sysMenuMapper.queryMenuByRoleId(roleId);

    }

    @Override
    public PageUtils queryPage(SysMenuQueryDto queryDTO) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.like("name",queryDTO.getName());
        wrapper.eq("parent_id",0);
        List<SysMenu> parentMenus = getParentMenu(wrapper);
        System.out.println(parentMenus);
        Page<SysMenu> page = this.page(queryDTO.page(),wrapper);
        page.setRecords(parentMenus);
        return new PageUtils(page);

    }

    @Override

    public String checkMenuName(String name) {
        int count= this.count(new QueryWrapper<SysMenu>().eq(StringUtils.isNotEmpty(name),"name", name));
        if(count>0){
            return "fail";
        }
        return "success";
    }

    @Override
    @SystemLog(value = "新增菜单")
    public boolean saveMenu(SysMenu sysMenu) {
        sysMenu.setCreateTime(LocalDateTime.now());
        sysMenu.setUpdateTime(LocalDateTime.now());
        this.baseMapper.insert(sysMenu);


        return true;
    }

    @Override
    @SystemLog(value = "更新菜单")
    public boolean updateMenu(SysMenu sysMenu) {
        sysMenu.setUpdateTime(LocalDateTime.now());
        this.updateById(sysMenu);

        return true;
    }

    @Override
    @SystemLog(value = "删除菜单")
    public String deleteMenu(Long menuId) {
        List<SysRole> sysRoles = sysMenuMapper.queryRoleByMenuId(menuId);
        if(sysRoles!=null&&sysRoles.size()>0){
            return "fail";
        }
        List<SysMenu> childMenus= sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("parent_id",menuId));
        if(childMenus!=null&&childMenus.size()>0){
            return "fail";
        }
        QueryWrapper<SysMenu> wrapper= new QueryWrapper<>();
        wrapper.eq("menu_id",menuId);
        this.sysMenuMapper.delete(wrapper);
        return "success";
    }

    @Override
    public List<SysMenu> getNowMenuChecked() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        String username=(String) authentication.getPrincipal();
        Long userId=sysUserMapper.getUserIdByUserName(username);
        List<SysRole> sysRoles = sysRoleMapper.queryRoleByUserId(userId);
        Set<SysMenu> sysMenuSet=new HashSet<>();
        for (SysRole sysRole : sysRoles) {
            List<Long> menuList = sysMenuMapper.queryMenuByRoleId(sysRole.getRoleId());
            QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
            wrapper.eq("parent_id",0);
            wrapper.in("menu_id",menuList);


            List<SysMenu> sysMenus = getParentMenu(wrapper);
            sysMenuSet.addAll(sysMenus);
        }
        System.out.println(sysMenuSet);

        return new ArrayList<>(sysMenuSet);
    }


}
