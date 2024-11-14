package com.wanshu.worker.controller;


import com.wanshu.common.util.PageUtils;
import com.wanshu.worker.dto.WorkerHourQueryDto;
import com.wanshu.worker.service.IWorkhoursService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
public class WorkhoursController {
    @Autowired
    private IWorkhoursService workhoursService;
    @GetMapping("/list")
    @ApiOperation(value = "分页查询工时")
    public PageUtils queryPage(@ApiParam(value = "加工名")WorkerHourQueryDto workerHourQueryDto){
        log.info("queryPage{}",workerHourQueryDto);

        return this.workhoursService.queryPageWorkerHour(workerHourQueryDto);
    }
}
