package com.wanshu.worker.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanshu.common.util.PageUtils;
import com.wanshu.worker.dto.WorkerHourQueryDto;
import com.wanshu.worker.entity.Workhours;

/**
 * <p>
 * 工时表 服务类
 * </p>
 *
 * @author hehe
 * @since 2024-11-03
 */
public interface IWorkhoursService extends IService<Workhours> {

    PageUtils queryPageWorkerHour(WorkerHourQueryDto workerHourQueryDto);

    boolean saveworkhours(Workhours workhours);

    String deleteWorkhours(int id);

    boolean updateWorkhours(Workhours workhours);
}
