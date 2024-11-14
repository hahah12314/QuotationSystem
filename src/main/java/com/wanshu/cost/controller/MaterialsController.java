package com.wanshu.cost.controller;


import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.MaterialQueryDto;
import com.wanshu.cost.entity.Materials;
import com.wanshu.cost.service.IMaterialsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/getAll")
    @ApiOperation(value = "查询所有物料")
    public List<Materials> queryAll(){
        log.info("queryAll");
        return this.materialsService.queryAll();
    }

}
