package com.wanshu.worker.mapper;

import com.wanshu.worker.entity.Workhours;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 工时表 Mapper 接口
 * </p>
 *
 * @author hehe
 * @since 2024-11-03
 */
@Mapper
public interface WorkhoursMapper extends BaseMapper<Workhours> {

    List<Workhours> getWorkhoursByProcess(String processName);
}
