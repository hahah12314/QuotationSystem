package com.wanshu.worker.controller;


import com.wanshu.common.util.PageUtils;
import com.wanshu.worker.dto.WorkDto;
import com.wanshu.worker.dto.WorkerQueryDto;
import com.wanshu.worker.service.IWorkersService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工人表 前端控制器
 * </p>
 *
 * @author hehe
 * @since 2024-11-03
 */
@RestController
@RequestMapping("/workers")
@Slf4j
public class WorkersController {
    @Autowired
    private IWorkersService workersService;
    @GetMapping("/list")
    @ApiOperation(value = "分页查询工人")
    public PageUtils queryPage(@ApiParam(value = "工人名")WorkerQueryDto workerQueryDto){
        log.info("queryPage{}",workerQueryDto);

        return this.workersService.queryPageWorker(workerQueryDto);
    }
    @GetMapping("/getAll")
    @ApiOperation(value = "查询工人")
    public Map<String, List<WorkDto>> queryAll(){
        log.info("queryAll");

        return this.workersService.queryAll();
    }
}
