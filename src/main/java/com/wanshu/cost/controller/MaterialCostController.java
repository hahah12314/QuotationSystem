package com.wanshu.cost.controller;


import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.cost.entity.MaterialCost;
import com.wanshu.cost.service.IMaterialCostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 原材料费用表 前端控制器
 * </p>
 *
 * @author hehe
 * @since 2024-10-14
 */
@RestController
@Slf4j
@RequestMapping("/material-cost")
public class MaterialCostController {
    @Autowired
    private IMaterialCostService materialCostService;

    @GetMapping("/list")
    @ApiOperation(value = "分页查询原材料费用表")
    public PageUtils queryPage(@ApiParam(value = "材料规格") RawMaterialQueryDto rawMaterialQueryDto){
        log.info("queryPage{}",rawMaterialQueryDto);

        return materialCostService.queryPageMaterialCost(rawMaterialQueryDto);
    }

    @PostMapping("/save")
    @ApiOperation(value= "新增原材料费用表")
    public boolean save(@ApiParam(value = "原材料费用") @RequestBody MaterialCost materialCost){
        log.info("save:{}",materialCost);
        return this.materialCostService.saveMaterialCost(materialCost);
    }
    /*
    {
    "detailId": 9,
    "length": 200.00,
    "width": 100.00,
    "height": 50.00,
    "blankWeight": 300.00,
    "materialUnitPrice": 10.50,
    "blankFee": 3150.00,
    "scrapWeight": 30.00,
    "scrapUnitPrice": 2.00,
    "scrapFee": 60.00,
    "totalMaterialCost": 3210.00
}

     */
    //postman传json格式
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除原材料费用表")
    public String deleteMaterialCost(@RequestBody Map<String, Object> request) {
        int id = (int) request.get("id");
        log.info("deleteMaterialCost:{}", id);
        return this.materialCostService.deleteMaterialCost(id);
    }

    //postman传url形式
//    @DeleteMapping("/delete")
//    @ApiOperation(value = "删除原材料费用表")
//    public String deleteMaterialCost(@ApiParam(value = "id") @RequestParam("id") Integer id) {
//        log.info("deleteMaterialCost:{}", id);
//        return this.materialCostService.deleteMaterialCost(id);
//    }

    @PostMapping("/update")
    @ApiOperation(value = "更新原材料费用表")
    public boolean update(@ApiParam(value = "原材料费用表") @RequestBody MaterialCost materialCost){
        log.info("update:{}",materialCost);
        return this.materialCostService.updateMaterialCost(materialCost);

    }

}
