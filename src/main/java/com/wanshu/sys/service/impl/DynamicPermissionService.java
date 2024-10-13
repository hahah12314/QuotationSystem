package com.wanshu.sys.service.impl;


import com.wanshu.sys.entity.SysMenu;
import com.wanshu.sys.entity.SysRole;
import com.wanshu.sys.entity.SysUser;
import com.wanshu.sys.mapper.SysMenuMapper;
import com.wanshu.sys.service.ISysRoleService;
import com.wanshu.sys.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DynamicPermissionService {

    @Autowired
    ISysUserService sysUserService;

    @Autowired
    ISysRoleService sysRoleService;
    @Autowired
    SysMenuMapper sysMenuMapper;

    // 判断当前请求是否有权限
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        String username=(String) authentication.getPrincipal();
        // 获取当前用户的权限
        List<String> urls = getPermissionUrls(username);

        // 检查请求的URL是否在用户的权限列表中
        String requestUrl = request.getRequestURI();
        System.out.println(urls+" ---- "+requestUrl);
        System.out.println(urls.contains(requestUrl));
        return urls.contains(requestUrl);
    }
    public List<String> getPermissionUrls(String username) {

        List<SysUser> sysUsers = sysUserService.queryByUsername(username);
        if (sysUsers != null && sysUsers.size() == 1) {
            SysUser sysUser = sysUsers.get(0);
            List<SysRole> sysRoles = sysRoleService.queryRoleByUserId(sysUser.getUserId());
            List<String> authorities = new ArrayList<>();
            Set<SysMenu> menuSet=new HashSet<>();
            if (sysRoles != null && sysRoles.size() > 0) {
                for (SysRole sysRole : sysRoles) {
                    List<SysMenu> sysMenus = sysMenuMapper.queryMenuRoleByRoleId(sysRole.getRoleId());
                    menuSet.addAll(sysMenus);
                }

            }
            for (SysMenu sysMenu : menuSet) {
                String menuPath = sysMenu.getPath();
                if (menuPath != null && !menuPath.isEmpty()) {
                    // 使用 SimpleGrantedAuthority 创建 GrantedAuthority 对象
                    authorities.add(menuPath);
                }
            }


            return authorities;

        }
        return null;
    }
}

