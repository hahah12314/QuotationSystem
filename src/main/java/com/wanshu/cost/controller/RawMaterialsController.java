package com.wanshu.cost.controller;


import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.MaterialQueryDto;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.cost.service.IRawMaterialsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 原材料表 前端控制器
 * </p>
 *
 * @author hehe
 * @since 2024-10-12
 */
@RestController
@RequestMapping("/raw-materials")
@Slf4j
public class RawMaterialsController {
    @Autowired
    private IRawMaterialsService rawMaterialsService;
    @GetMapping("/list")
    @ApiOperation(value = "分页查询材料")
    public PageUtils queryPage(@ApiParam(value = "材料名")RawMaterialQueryDto rawMaterialQueryDto){
        log.info("queryPage{}",rawMaterialQueryDto);

        return this.rawMaterialsService.queryPageRawMaterial(rawMaterialQueryDto);
    }


}
