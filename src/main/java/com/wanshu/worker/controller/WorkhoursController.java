package com.wanshu.worker.controller;


import com.wanshu.common.util.PageUtils;
import com.wanshu.worker.dto.WorkerHourQueryDto;
import com.wanshu.worker.entity.Workhours;
import com.wanshu.worker.service.IWorkhoursService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 工时表 前端控制器
 * </p>
 *
 * @author hehe
 * @since 2024-11-03
 */
@RestController
@RequestMapping("/workhours")
@Slf4j
@Api(tags = "工时管理")
public class WorkhoursController {
    @Autowired
    private IWorkhoursService workhoursService;
    @GetMapping("/list")
    @ApiOperation(value = "分页查询工时")
    public PageUtils queryPage(@ApiParam(value = "加工名")WorkerHourQueryDto workerHourQueryDto){
        log.info("queryPage{}",workerHourQueryDto);

        return this.workhoursService.queryPageWorkerHour(workerHourQueryDto);
    }

    //lzy写的,如果发送json不带主键，为什么数据库里的序号id主键不是自增1了？
    @PostMapping("/save")
    @ApiOperation(value= "新增工时")
    public boolean save(@ApiParam(value = "工时信息") @RequestBody Workhours workhours){
        log.info("save:{}",workhours);
        return this.workhoursService.saveworkhours(workhours);
    }
    /*
    {
    "id" :23,
    "processName": "钻孔",
    "workerId": 3,
    "cost": 20.00,
    "hour": 1.5
}

     */

    //postman传json格式
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除工时")
    public String deleteWorkhours(@RequestBody Map<String, Object> request) {
        int id = (int) request.get("id");
        log.info("deleteWorkhours:{}", id);
        return this.workhoursService.deleteWorkhours(id);
    }


    //postman传url形式
//    @DeleteMapping("/delete")
//    @ApiOperation(value = "删除工时")
//    public String deleteWorkhours(@ApiParam(value = "id") @RequestParam("id") Integer id) {
//        log.info("deleteWorkhours:{}", id);
//        return this.workhoursService.deleteWorkhours(id);
//    }



    @PostMapping("/update")
    @ApiOperation(value = "更新工时表")
    public boolean update(@ApiParam(value = "工时表") @RequestBody Workhours workhours){
        log.info("update:{}",workhours);
        return this.workhoursService.updateWorkhours(workhours);

    }
}
