package com.wanshu.cost.controller;


import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.cost.entity.RawMaterials;
import com.wanshu.cost.service.IRawMaterialsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
@Api(tags = "原材料管理")
public class RawMaterialsController {
    @Autowired
    private IRawMaterialsService rawMaterialsService;
    @GetMapping("/list")
    @ApiOperation(value = "分页查询材料")
    public PageUtils queryPage(@ApiParam(value = "材料名")RawMaterialQueryDto rawMaterialQueryDto){
        log.info("queryPage{}",rawMaterialQueryDto);

        return this.rawMaterialsService.queryPageRawMaterial(rawMaterialQueryDto);
    }
    @GetMapping("/getDetail")
    @ApiOperation(value = "获取原材料详情")
    public Map<String,Object> getDetail(@ApiParam(value = "详情ID") @RequestParam Long detailId) {
        log.info("getDetail, detailId: {}", detailId);
        return this.rawMaterialsService.getRawMaterialDetail(detailId);
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "查询所有材料")
    public List<RawMaterials> queryAll(){
        log.info("queryAll");
        return this.rawMaterialsService.queryAll();
    }



    @PostMapping("/save")
    @ApiOperation(value= "新增原材料表")
    public boolean save(@ApiParam(value = "原材料") @RequestBody RawMaterials rawMaterials){
        log.info("save:{}",rawMaterials);
        return this.rawMaterialsService.saverawMaterials(rawMaterials);
    }
    /*
    {
    "detailId": 9,
    "nakedPrice": 100.00,
    "profit": 20.00,
    "miscellaneousFees": 5.00,
    "totalProductQuote": 125.00,
    "specification": "Spec A",
    "unitWeight": 10.00,
    "netWeight": 8.00,
    "preTax": 110.00,
    "pricePerkg": 12.50
    }
}
     */

    //这个表主键是detailed_id
    //postman传json格式
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除原材料表")
    public String deleteRawMaterials(@RequestBody Map<String, Object> request) {
        int id = (int) request.get("detail_id");
        log.info("deleteRawMaterials:{}", id);
        return this.rawMaterialsService.deleteRawMaterials(id);
    }
    /*
    {
    "detail_id" : 7
}
     */

    //postman传url格式
//    @DeleteMapping("/delete")
//    @ApiOperation(value = "删除原材料表")
//    public String deleteRawMaterials(@ApiParam(value = "id") @RequestParam("id") Integer id) {
//        log.info("deleteRawMaterials:{}", id);
//        return this.rawMaterialsService.deleteRawMaterials(id);
//    }

    @PostMapping("/update")
    @ApiOperation(value = "更新原材料表")
    public boolean update(@ApiParam(value = "原材料表") @RequestBody RawMaterials rawMaterials){
        log.info("update:{}",rawMaterials);
        return this.rawMaterialsService.updateRawMaterials(rawMaterials);

    }

}
