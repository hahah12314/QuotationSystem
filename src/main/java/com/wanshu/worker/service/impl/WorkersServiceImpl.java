package com.wanshu.worker.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanshu.common.annotation.SystemLog;
import com.wanshu.common.util.PageUtils;
import com.wanshu.worker.dto.WorkDto;
import com.wanshu.worker.dto.WorkerQueryDto;
import com.wanshu.worker.entity.Workers;
import com.wanshu.worker.entity.Workhours;
import com.wanshu.worker.mapper.WorkersMapper;
import com.wanshu.worker.mapper.WorkhoursMapper;
import com.wanshu.worker.service.IWorkersService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 工人表 服务实现类
 * </p>
 *
 * @author hehe
 * @since 2024-11-03
 */
@Service
public class WorkersServiceImpl extends ServiceImpl<WorkersMapper, Workers> implements IWorkersService {
    @Autowired
    private WorkersMapper workersMapper;
    @Autowired
    private WorkhoursMapper workerWorkhourMapper;
    @Override
    public PageUtils queryPageWorker(WorkerQueryDto workerQueryDto) {
        QueryWrapper<Workers> wrapper=  new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(workerQueryDto.getWorkerName()), "worker_name", workerQueryDto.getWorkerName());
        Page<Workers> page = this.page(workerQueryDto.page(),wrapper);

        return new PageUtils(page);
    }

    @Override
    public Map<String, List<WorkDto>> queryAll() {
        List<String> processes = Arrays.asList("锯", "弯", "钻", "车", "外磨", "铣", "校平", "镗铣", "焊", "打磨", "装");
        Map<String, List<WorkDto>> result = new HashMap<>();

        for (String process : processes) {
            List<Workhours> workhours = workerWorkhourMapper.getWorkhoursByProcess(process);
            List<WorkDto> workerWorkhours = new ArrayList<>();

            for (Workhours workhour : workhours) {
                Workers worker = workersMapper.getWorkerById(workhour.getWorkerId());
                WorkDto workerWorkhour = new WorkDto();
                BeanUtils.copyProperties(workhour, workerWorkhour); // 拷贝 Workhours 的属性
                workerWorkhour.setWorkerName(worker.getWorkerName()); // 设置工人姓名
                workerWorkhour.setLevel(worker.getLevel()); // 设置工人等级
                workerWorkhours.add(workerWorkhour);
            }

            result.put(process, workerWorkhours);
        }

        return result;
    }

    //lzy写的

    @Override
    @SystemLog(value = "新增工人")
    public boolean saveworkers(Workers workers) {
        this.baseMapper.insert(workers);
        return true;
    }

    @Override
    @Transactional
    @SystemLog(value = "删除工人")
    public String deleteWorkers(Integer id) {
        //System.out.println("Service"+id);
        this.baseMapper.deleteWorkerById(id);
        return "success";
    }

    @Override
    @ApiOperation(value = "更新工人")
    @SystemLog(value = "更新工人")
    public boolean updateWorkers(Workers workers) {
        this.updateById(workers);
        return true;
    }


}
