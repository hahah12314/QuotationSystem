package com.wanshu.cost.controller;


import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.cost.entity.ProcessingCost;
import com.wanshu.cost.service.IProcessingCostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 单件加工费用表 前端控制器
 * </p>
 *
 * @author hehe
 * @since 2024-10-14
 */
@RestController
@Slf4j
@Api(tags = "加工费用管理")
@RequestMapping("/processing-cost")
public class ProcessingCostController {
    @Autowired
    private IProcessingCostService processingCostService;
    @GetMapping("/list")
    @ApiOperation(value = "分页查询加工表")
    public PageUtils queryPage(@ApiParam(value = "材料规格") RawMaterialQueryDto rawMaterialQueryDto){
        log.info("queryPage{}",rawMaterialQueryDto);

        return processingCostService.queryPageProcessCost(rawMaterialQueryDto);
    }

    @PostMapping("/save")
    @ApiOperation(value= "新增加工表")
    public boolean save(@ApiParam(value = "加工") @RequestBody ProcessingCost processingCost){
        log.info("save:{}",processingCost);
        return this.processingCostService.saveProcessingCost(processingCost);
    }
    /*
    {
    "id": 8,
    "detailId": 9,
    "sawTime": 1.5,
    "sawWorkhourId": 1,
    "sawFee": 20.00,
    "bendTime": 1.2,
    "bendFee": 15.00,
    "bendWorkhourId": 1,
    "drillTime": 2.0,
    "drillFee": 30.00,
    "drillWorkhourId": 1,
    "turnTime": 1.0,
    "turnFee": 10.00,
    "turnWorkhourId": 1,
    "grindTime": 1.8,
    "grindFee": 25.00,
    "grindWorkhourId": 1,
    "millTime": 2.5,
    "millFee": 40.00,
    "millWorkhourId": 1,
    "calibrateTime": 1.0,
    "calibrateFee": 12.00,
    "calibrateWorkhourId": 1,
    "boreMillTime": 1.2,
    "boreMillFee": 18.00,
    "boreMillWorkhourId": 1,
    "weldTime": 2.0,
    "weldFee": 30.00,
    "weldWorkhourId": 1,
    "polishTime": 1.5,
    "polishFee": 20.00,
    "polishWorkhourId": 1,
    "assembleTime": 3.0,
    "assembleFee": 50.00,
    "assembleWorkhourId": 1,
    "totalProcessCost": 337.00
}

     */
    //postman传json格式
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除加工表")
    public String deleteProcessingCost(@RequestBody Map<String, Object> request) {
        int id = (int) request.get("id");
        log.info("deleteProcessingCost:{}", id);
        return this.processingCostService.deleteProcessingCost(id);
    }

    //postman传url格式
//    @DeleteMapping("/delete")
//    @ApiOperation(value = "删除加工表")
//    public String deleteProcessingCost(@ApiParam(value = "id") @RequestParam("id") Integer id) {
//        log.info("deleteProcessingCost:{}", id);
//        return this.processingCostService.deleteProcessingCost(id);
//    }

    @PostMapping("/update")
    @ApiOperation(value = "更新加工表")
    public boolean update(@ApiParam(value = "加工表") @RequestBody ProcessingCost processingCost){
        log.info("update:{}",processingCost);
        return this.processingCostService.updateProcessingCost(processingCost);

    }

}
