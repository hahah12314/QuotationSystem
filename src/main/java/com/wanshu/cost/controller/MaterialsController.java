package com.wanshu.cost.controller;


import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.MaterialQueryDto;
import com.wanshu.cost.entity.Materials;
import com.wanshu.cost.service.IMaterialsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    //为什么新增不按照id增
    @PostMapping("/save")
    @ApiOperation(value= "新增物料表")
    public boolean save(@ApiParam(value = "物料") @RequestBody Materials materials){
        log.info("save:{}",materials);
        return this.materialsService.saveMaterials(materials);
    }
    /*测试样例
    {
    "materialCode": "MAT005",
    "materialName": "铝材",
    "unitPrice": 120.50,
    "weight": 200.00,
    "createTime": "2024-11-18 14:00:00",
    "updateTime": "2024-11-18 14:00:00",
    "isExternalProcurement": 1,
    "quantity": 100
    }
     */

    //这个表主键是material_id

    //postman传json格式
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除物料表")
    public String deleteMaterials(@RequestBody Map<String, Object> request) {
        int id = (int) request.get("material_id");
        log.info("deleteMaterials:{}", id);
        return this.materialsService.deleteMaterials(id);
    }
    /*
    {
    "material_id" : 2
}
     */

    //postman传url格式
//    @DeleteMapping("/delete")
//    @ApiOperation(value = "删除物料表")
//    public String deleteMaterials(@ApiParam(value = "id") @RequestParam("id") Integer id) {
//        log.info("deleteMaterials:{}", id);
//        return this.materialsService.deleteMaterials(id);
//    }

    @PostMapping("/update")
    @ApiOperation(value = "更新物料表")
    public boolean update(@ApiParam(value = "物料表") @RequestBody Materials materials){
        log.info("update:{}",materials);
        return this.materialsService.updateMaterials(materials);

    }


}
