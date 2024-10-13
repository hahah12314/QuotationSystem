package com.wanshu.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanshu.common.util.PageUtils;
import com.wanshu.sys.dto.SysMenuQueryDto;
import com.wanshu.sys.entity.SysMenu;
import com.wanshu.sys.vo.SysMenuVo;


import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author hehe
 * @since 2024-10-08
 */
public interface ISysMenuService extends IService<SysMenu> {

    SysMenuVo queryMenuAll();


    List<Long> queryMenuByRoleId(Long roleId);

    PageUtils queryPage(SysMenuQueryDto queryDTO);

    String checkMenuName(String name);

    boolean saveMenu(SysMenu sysMenu);

    boolean updateMenu(SysMenu sysMenu);

    String deleteMenu(Long menuId);

    List<SysMenu> getNowMenuChecked();
}
