package com.wanshu.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanshu.common.util.PageUtils;
import com.wanshu.sys.dto.SysUserQueryDto;
import com.wanshu.sys.entity.SysUser;


import java.util.List;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author hehe
 * @since 2024-10-08
 */
public interface ISysUserService extends IService<SysUser> {

    List<SysUser> queryByUsername(String username);


    PageUtils queryPageUser(SysUserQueryDto sysUserQueryDto);

    boolean updateUser(SysUser sysUser);

    boolean saveUser(SysUser sysUser);

    String checkUsername(String username);

    String deleteBatch(List<Long> ids);
}
