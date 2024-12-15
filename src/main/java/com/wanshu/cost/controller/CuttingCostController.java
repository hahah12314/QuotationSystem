package com.wanshu.cost.controller;


import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.cost.entity.CuttingCost;
import com.wanshu.cost.service.ICuttingCostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 数割费用表 前端控制器
 * </p>
 *
 * @author hehe
 * @since 2024-10-14
 */
@RestController
@Slf4j
@Api(tags = "数割费用管理")
@RequestMapping("/cutting-cost")
public class CuttingCostController {
    @Autowired
    private ICuttingCostService cuttingCostService;

    @GetMapping("/list")
    @ApiOperation(value = "分页查询数割表")
    public PageUtils queryPage(@ApiParam(value = "材料规格") RawMaterialQueryDto rawMaterialQueryDto){
        log.info("queryPage{}",rawMaterialQueryDto);

        return cuttingCostService.queryPageCuttingCost(rawMaterialQueryDto);
    }

    @PostMapping("/save")
    @ApiOperation(value= "新增数割表")
    public boolean save(@ApiParam(value = "数割表信息") @RequestBody CuttingCost cuttingCost){
        log.info("save:{}",cuttingCost);
        return this.cuttingCostService.saveCuttingCost(cuttingCost);
    }
    /*测试新增数割表样例
    {
    "detailId": 9,
    "numCut": 5,
    "cutFee": 50.00,
    "totalCutCost": 250.00
}
     */
    //postman传json格式
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除数割表")
    public String deleteCuttingCost(@RequestBody Map<String, Object> request) {
        int id = (int) request.get("id");
        log.info("deleteCuttingCost:{}", id);
        return this.cuttingCostService.deleteCuttingCost(id);
    }

    //postman传url形式
//    @DeleteMapping("/delete")
//    @ApiOperation(value = "删除数割表")
//    public String deleteCuttingCost(@ApiParam(value = "id") @RequestParam("id") Integer id) {
//        log.info("deleteCuttingCost:{}", id);
//        return this.cuttingCostService.deleteCuttingCost(id);
//    }

    @PostMapping("/update")
    @ApiOperation(value = "更新数割表")
    public boolean update(@ApiParam(value = "数割表信息") @RequestBody CuttingCost cuttingCost){
        log.info("update:{}",cuttingCost);
        return this.cuttingCostService.updateCuttingCost(cuttingCost);

    }


}
