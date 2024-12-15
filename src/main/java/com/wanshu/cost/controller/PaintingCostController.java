package com.wanshu.cost.controller;


import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.cost.entity.PaintingCost;
import com.wanshu.cost.service.IPaintingCostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 喷涂单件费用表 前端控制器
 * </p>
 *
 * @author hehe
 * @since 2024-10-14
 */
@RestController
@Slf4j
@Api(tags = "喷涂费用管理")
@RequestMapping("/painting-cost")
public class PaintingCostController {
    @Autowired
    private IPaintingCostService paintingCostService;

    @GetMapping("/list")
    @ApiOperation(value = "分页查询喷涂表")
    public PageUtils queryPage(@ApiParam(value = "材料规格") RawMaterialQueryDto rawMaterialQueryDto){
        log.info("queryPage{}",rawMaterialQueryDto);

        return paintingCostService.queryPagePaintingCost(rawMaterialQueryDto);
    }

    @PostMapping("/save")
    @ApiOperation(value= "新增喷涂表")
    public boolean save(@ApiParam(value = "喷涂") @RequestBody PaintingCost paintingCost){
        log.info("save:{}",paintingCost);
        return this.paintingCostService.savePaintingCost(paintingCost);
    }
    /*
    {
    "id" : 12,
    "detailId": 9,
    "acidWashCost": 2.00,
    "acidWashWeight": 150.00,
    "sprayPlasticCost": 3.50,
    "primerCost": 1.50,
    "primerArea": 100.00,
    "sprayPlasticArea": 80.00,
    "electroswimmingCost": 5.00,
    "electroswimmingArea": 120.00,
    "totalPaintingCost": 250.00,
    "topcoatCost": 2.50,
    "topcoatArea": 50.00
}
     */
    //postman传json格式
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除喷涂表")
    public String deletePaintingCost(@RequestBody Map<String, Object> request) {
        int id = (int) request.get("id");
        log.info("deletePaintingCost:{}", id);
        return this.paintingCostService.deletePaintingCost(id);
    }

    //postman传url格式
//    @DeleteMapping("/delete")
//    @ApiOperation(value = "删除喷涂表")
//    public String deletePaintingCost(@ApiParam(value = "id") @RequestParam("id") Integer id) {
//        log.info("deletePaintingCost:{}", id);
//        return this.paintingCostService.deletePaintingCost(id);
//    }

    @PostMapping("/update")
    @ApiOperation(value = "更新喷涂表")
    public boolean update(@ApiParam(value = "喷涂表") @RequestBody PaintingCost paintingCost){
        log.info("update:{}",paintingCost);
        return this.paintingCostService.updatePaintingCost(paintingCost);

    }
}
