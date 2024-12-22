package com.wanshu.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanshu.common.util.PageUtils;
import com.wanshu.sys.dto.SysLogQueryDto;
import com.wanshu.sys.entity.SysLog;

import java.util.List;


/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author hehe
 * @since 2024-10-08
 */
public interface ISysLogService extends IService<SysLog> {

    PageUtils queryPage(SysLogQueryDto sysLogQueryDto);

    String deleteLog(Long id);

    List<SysLog> getLatestLogs(int limit);
}
