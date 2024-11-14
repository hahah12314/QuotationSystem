package com.wanshu.worker.service;

import com.wanshu.common.util.PageUtils;
import com.wanshu.worker.dto.WorkDto;
import com.wanshu.worker.dto.WorkerQueryDto;
import com.wanshu.worker.entity.Workers;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工人表 服务类
 * </p>
 *
 * @author hehe
 * @since 2024-11-03
 */
public interface IWorkersService extends IService<Workers> {

    PageUtils queryPageWorker(WorkerQueryDto workerQueryDto);

    Map<String, List<WorkDto>> queryAll();
}
