package com.wanshu.sys.controller;



import com.wanshu.common.util.PageUtils;
import com.wanshu.sys.dto.SysRoleQueryDto;
import com.wanshu.sys.entity.SysRole;
import com.wanshu.sys.service.ISysRoleService;
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
 * 角色 前端控制器
 * </p>
 *
 * @author hehe
 * @since 2024-10-08
 */
@RestController
@Slf4j
@Api(tags = "角色管理")
@RequestMapping("/sys/sysRole")
public class SysRoleController {
    @Autowired
    private ISysRoleService sysRoleService;
    @GetMapping("/roleAll")
    @ApiOperation(value = "查询所有角色")
    public List<SysRole> roleAll(){
        return sysRoleService.list();
    }

    @GetMapping("/getRoleChecked")
    @ApiOperation(value = "根据用户id查询角色")
    private List<SysRole> queryRoleByUser(@Parameter(description = "用户id") Long userId){
          return sysRoleService.queryRoleByUserId(userId);

    }

    @GetMapping("/list")
    @ApiOperation(value = "分页查询角色")
    public PageUtils queryPage(@ApiParam(value = "角色名") SysRoleQueryDto sysRoleQueryDto){
        log.info("queryPage{}",sysRoleQueryDto);

        return this.sysRoleService.queryPageRole(sysRoleQueryDto);
    }
    @PostMapping("/update")
    @ApiOperation(value = "更新角色")
    public boolean update(@ApiParam(value = "角色信息") @RequestBody SysRole sysRole){
        log.info("update:{}",sysRole);
        return this.sysRoleService.updateRole(sysRole);

    }
    @PostMapping("/save")
    @ApiOperation(value = "新增角色")
    public boolean save(@ApiParam(value = "角色信息") @RequestBody SysRole sysRole){
        log.info("save:{}",sysRole);
        return this.sysRoleService.saveRole(sysRole);

    }
    @GetMapping("/checkRoleName")
    @ApiOperation(value = "检查角色名是否重复")
    public String checkRoleName(@ApiParam(value = "角色名") String roleName) {
        return this.sysRoleService.checkRoleName(roleName);
    }
    @GetMapping("/delete")
    @ApiOperation(value = "删除角色")
    public String deleteRole(@ApiParam(value = "角色id") Long roleId){
        log.info("deleteRole:{}",roleId);
        return this.sysRoleService.deleteRole(roleId);
    }


}
