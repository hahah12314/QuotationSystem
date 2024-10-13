package com.wanshu.sys.service.impl;


import com.wanshu.sys.entity.SysUser;
import com.wanshu.sys.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证校验的方法
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    DynamicPermissionService dynamicPermissionService;
    @Autowired
    ISysUserService sysUserService;

    /**
     * 完成账号的校验
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<SysUser> sysUsers = sysUserService.queryByUsername(username);
        if (sysUsers != null && sysUsers.size() == 1) {
            SysUser sysUser = sysUsers.get(0);
            List<String> permissionUrls = dynamicPermissionService.getPermissionUrls(username);
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String url : permissionUrls) {
                authorities.add(new SimpleGrantedAuthority(url));
            }
            System.out.println("authorities:"+authorities);




            return new User(username, sysUser.getPassword(), authorities);

        }
        return null;
    }
}
