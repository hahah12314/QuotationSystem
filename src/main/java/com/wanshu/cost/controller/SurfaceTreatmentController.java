package com.wanshu.cost.controller;


import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.cost.entity.SurfaceTreatment;
import com.wanshu.cost.service.ISurfaceTreatmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
@Api(tags = "表面处理费用管理")
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

    @PostMapping("/save")
    @ApiOperation(value= "新增表面处理费用表")
    public boolean save(@ApiParam(value = "表面处理费用") @RequestBody SurfaceTreatment surfaceTreatment){
        log.info("save:{}",surfaceTreatment);
        return this.surfaceTreatmentService.saveSurfaceTreatment(surfaceTreatment);
    }
    /*
    {
    "detailId": 9,
    "zincWeight": 2.5,
    "zincFee": 25.00,
    "temperingWeight": 3.0,
    "temperingFee": 30.00,
    "sandWeight": 1.5,
    "sandFee": 15.00,
    "qpqWeight": 2.2,
    "qpqFee": 22.00,
    "totalCoatingCost": 92.00,
    "nickArea": 1.8,
    "nickFee": 18.00,
    "chromeArea": 2.0,
    "chromeFee": 20.00
}

     */
    //postman传json格式
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除表面处理费用表")
    public String deleteSurfaceTreatment(@RequestBody Map<String, Object> request) {
        int id = (int) request.get("id");
        log.info("deleteSurfaceTreatment:{}", id);
        return this.surfaceTreatmentService.deleteSurfaceTreatment(id);
    }

    //postman传json格式
//    @DeleteMapping("/delete")
//    @ApiOperation(value = "删除表面处理费用表")
//    public String deleteSurfaceTreatment(@ApiParam(value = "id") @RequestParam("id") Integer id) {
//        log.info("deleteSurfaceTreatment:{}", id);
//        return this.surfaceTreatmentService.deleteSurfaceTreatment(id);
//    }

    @PostMapping("/update")
    @ApiOperation(value = "更新表面处理费用表")
    public boolean update(@ApiParam(value = "表面处理费用表") @RequestBody SurfaceTreatment surfaceTreatment){
        log.info("update:{}",surfaceTreatment);
        return this.surfaceTreatmentService.updateSurfaceTreatment(surfaceTreatment);

    }
}
