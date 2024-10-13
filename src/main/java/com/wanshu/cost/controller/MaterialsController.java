package com.wanshu.cost.controller;


import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.MaterialQueryDto;
import com.wanshu.cost.mapper.MaterialsMapper;
import com.wanshu.cost.service.IMaterialsService;
import com.wanshu.sys.dto.SysRoleQueryDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 物料表 前端控制器
 * </p>
 *
 * @author hehe
 * @since 2024-10-12
 */
@RestController
@RequestMapping("/materials")
@Slf4j
@CrossOrigin
public class MaterialsController {
    @Autowired
    private IMaterialsService materialsService;
    @GetMapping("/list")
    @ApiOperation(value = "分页查询物料")
    public PageUtils queryPage(@ApiParam(value = "物料名") MaterialQueryDto materialQueryDto){
        log.info("queryPage{}",materialQueryDto);

        return this.materialsService.queryPageMaterial(materialQueryDto);
    }

}
