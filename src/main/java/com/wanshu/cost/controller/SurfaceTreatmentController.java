package com.wanshu.cost.controller;


import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.cost.service.ICuttingCostService;
import com.wanshu.cost.service.ISurfaceTreatmentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 表面处理单件费用表 前端控制器
 * </p>
 *
 * @author hehe
 * @since 2024-10-14
 */
@RestController
@Slf4j
@RequestMapping("/surface-treatment")
public class SurfaceTreatmentController {
    @Autowired
    private ISurfaceTreatmentService surfaceTreatmentService;

    @GetMapping("/list")
    @ApiOperation(value = "分页查询表面处理费用表")
    public PageUtils queryPage(@ApiParam(value = "材料规格") RawMaterialQueryDto rawMaterialQueryDto){
        log.info("queryPage{}",rawMaterialQueryDto);

        return surfaceTreatmentService.queryPageSufaceCost(rawMaterialQueryDto);
    }
}
