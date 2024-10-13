package com.wanshu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wanshu.common.annotation.SystemLog;
import com.wanshu.common.util.PageUtils;
import com.wanshu.sys.dto.SysLogQueryDto;
import com.wanshu.sys.entity.SysLog;
import com.wanshu.sys.mapper.SysLogMapper;
import com.wanshu.sys.service.ISysLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author hehe
 * @since 2024-10-08
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @Override
    public PageUtils queryPage(SysLogQueryDto sysLogQueryDto) {
        QueryWrapper<SysLog> wrapper=new QueryWrapper<>();
        wrapper.like("username",sysLogQueryDto.getMsg());
        wrapper.or().like("operation",sysLogQueryDto.getMsg());
        Page<SysLog> page=this.page(sysLogQueryDto.page(),wrapper);
        return new PageUtils(page);
    }

    @Override
    @SystemLog(value = "删除日志")
    public String deleteLog(Long id) {
        this.removeById(id);

        return "success";
    }
}
