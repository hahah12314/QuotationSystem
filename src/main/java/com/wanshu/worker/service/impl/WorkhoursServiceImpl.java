package com.wanshu.worker.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanshu.common.annotation.SystemLog;
import com.wanshu.common.util.PageUtils;
import com.wanshu.worker.dto.WorkHourDto;
import com.wanshu.worker.dto.WorkerHourQueryDto;
import com.wanshu.worker.entity.Workhours;
import com.wanshu.worker.mapper.WorkersMapper;
import com.wanshu.worker.mapper.WorkhoursMapper;
import com.wanshu.worker.service.IWorkhoursService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 工时表 服务实现类
 * </p>
 *
 * @author hehe
 * @since 2024-11-03
 */
@Service
public class WorkhoursServiceImpl extends ServiceImpl<WorkhoursMapper, Workhours> implements IWorkhoursService {

    @Autowired
    private WorkersMapper workersMapper;


    @Override
    public PageUtils queryPageWorkerHour(WorkerHourQueryDto workerHourQueryDto) {
        QueryWrapper<Workhours> wrapper=new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(workerHourQueryDto.getProcessName()),"process_name",workerHourQueryDto.getProcessName());

        // 执行分页查询
        Page<Workhours> page = this.page(workerHourQueryDto.page(), wrapper);

        // 获取 workerName
        List<WorkHourDto> dtoList = page.getRecords().stream()
                .map(workhours -> {
                    WorkHourDto dto = new WorkHourDto();
                    BeanUtils.copyProperties(workhours, dto);
                    // 从 worker 表中获取 workerName
                    String workerName = workersMapper.getWorkerName(workhours.getWorkerId());
                    dto.setWorkerName(workerName);
                    return dto;
                })
                .collect(Collectors.toList());
        System.out.println("workerhour-dtoList = " + dtoList);

        // 创建新的 Page 对象，包含转换后的 DTO 列表
        Page<WorkHourDto> dtoPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        dtoPage.setRecords(dtoList);

        // 返回分页结果
        return new PageUtils(dtoPage);
    }

    @Override
    public boolean saveworkhours(Workhours workhours) {
        this.baseMapper.insert(workhours);
        return true;
    }

    @Override
    @Transactional
    @SystemLog(value = "删除工人")
    public String deleteWorkhours(int id) {
        this.baseMapper.deleteWorkhoursById(id);
        return "success";
    }

    @Override
    public boolean updateWorkhours(Workhours workhours) {
        this.updateById(workhours);
        return true;
    }

}
