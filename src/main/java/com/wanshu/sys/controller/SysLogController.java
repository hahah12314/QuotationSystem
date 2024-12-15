package com.wanshu.sys.controller;



import com.wanshu.common.util.PageUtils;
import com.wanshu.sys.dto.SysLogQueryDto;
import com.wanshu.sys.service.ISysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author hehe
 * @since 2024-10-08
 */
@RestController
@Slf4j
@RequestMapping("/sys/sysLog")
@Api(tags = "日志管理")
public class SysLogController {
    @Autowired
    private ISysLogService sysLogService;
    @GetMapping("/list")
    @ApiOperation(value = "查询分页日志",notes = "日志信息")
    public PageUtils list(@ApiParam(value = "查询的条件") SysLogQueryDto sysLogQueryDto){

        log.info("sysLogQueryDto:{}",sysLogQueryDto);
        return sysLogService.queryPage(sysLogQueryDto);
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除日志")
    public String deleteLog(@ApiParam(value = "日志id") Long id){
        log.info("deleteLog:{}",id);
        return this.sysLogService.deleteLog(id);
    }

}
