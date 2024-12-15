package com.wanshu.sys.controller;



import com.wanshu.common.util.PageUtils;
import com.wanshu.sys.dto.SysMenuQueryDto;
import com.wanshu.sys.entity.SysMenu;
import com.wanshu.sys.service.ISysMenuService;
import com.wanshu.sys.vo.SysMenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author hehe
 * @since 2024-10-08
 */
@RestController
@RequestMapping("/sys/sysMenu")
@Slf4j
@Api(tags = "菜单管理")
public class SysMenuController {
    @Autowired
    private ISysMenuService sysMenuService;
    @GetMapping("/menuAll")
    @ApiOperation(value = "查询所有菜单")
    public SysMenuVo menuAll()
    {
        return sysMenuService.queryMenuAll();

    }
    @GetMapping("/getMenuChecked")
    @ApiOperation(value = "根据角色id查询菜单")
    private List<Long> queryCheckedMenuByRole(@Parameter(description = "角色id") Long roleId){
        System.out.println("roleId = " + roleId);
        return sysMenuService.queryMenuByRoleId(roleId);

    }
    @GetMapping("/getNowMenuChecked")
    @ApiOperation(value = "根据用户查询菜单")
    private List<SysMenu> getNowMenuChecked(){

        return sysMenuService.getNowMenuChecked();

    }
    @ApiOperation(value = "查询分页菜单",notes = "菜单信息")
    @GetMapping("/list")
    public PageUtils list(@ApiParam(value = "查询的条件") SysMenuQueryDto queryDTO){
        log.info("menu-queryDTO:{}",queryDTO);
        return sysMenuService.queryPage(queryDTO);
    }
    @GetMapping("/checkMenuName")
    @ApiOperation(value = "检查菜单名是否重复")
    public String checkRoleName(@ApiParam(value = "菜单名") String name) {
        log.info("name:{}",name);
        return this.sysMenuService.checkMenuName(name);
    }
    @PostMapping("/save")
    @ApiOperation(value = "新增菜单")
    public boolean save(@ApiParam(value = "菜单信息") @RequestBody SysMenu sysMenu){
        log.info("save:{}",sysMenu);
        return this.sysMenuService.saveMenu(sysMenu);

    }
    @PostMapping("/update")
    @ApiOperation(value = "更新菜单")
    public boolean update(@ApiParam(value = "菜单信息") @RequestBody SysMenu sysMenu){
        log.info("update:{}",sysMenu);
        return this.sysMenuService.updateMenu(sysMenu);

    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除菜单")
    public String deleteMenu(@ApiParam(value = "菜单id") Long menuId) {
        log.info("deleteMenu:{}", menuId);
        return this.sysMenuService.deleteMenu(menuId);
    }

}
