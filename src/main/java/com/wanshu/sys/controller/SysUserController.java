package com.wanshu.sys.controller;



import com.wanshu.common.util.PageUtils;
import com.wanshu.sys.dto.SysUserQueryDto;
import com.wanshu.sys.entity.SysUser;
import com.wanshu.sys.service.ISysUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author hehe
 * @since 2024-10-08
 */
@RestController
@RequestMapping("/sys/sysUser")
@Slf4j
public class SysUserController {
    @Autowired
    private ISysUserService sysUserService;
    @GetMapping("/list")
    @ApiOperation(value = "分页查询用户")
    public PageUtils queryPage(@ApiParam(value = "用户名") SysUserQueryDto sysUserQueryDto){
        log.info("queryPage",sysUserQueryDto);

        return this.sysUserService.queryPageUser(sysUserQueryDto);
    }
    @PostMapping("/update")
    @ApiOperation(value = "更新用户")
    public boolean update(@ApiParam(value = "用户信息") @RequestBody SysUser sysUser){
        log.info("update:{}",sysUser);
        return this.sysUserService.updateUser(sysUser);

    }
    @PostMapping("/save")
    @ApiOperation(value = "新增用户")
    public boolean save(@ApiParam(value = "用户信息") @RequestBody SysUser sysUser){
        log.info("save:{}",sysUser);
        return this.sysUserService.saveUser(sysUser);

    }
    @GetMapping("/checkuserName")
    @ApiOperation(value = "检查用户名是否重复")
    public String checkUsername(@ApiParam(value = "用户名") String username) {
       return this.sysUserService.checkUsername(username);
    }
    @PostMapping("/deleteBatch")
    @ApiOperation(value = "批量删除用户")
    public String deleteBatch(@ApiParam(value = "用户id集合") @RequestBody List<Long> ids){
        log.info("deleteBatch{}",ids);
        return sysUserService.deleteBatch(ids);
    }


}
