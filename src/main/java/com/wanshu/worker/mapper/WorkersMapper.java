package com.wanshu.worker.mapper;

import com.wanshu.worker.entity.Workers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 工人表 Mapper 接口
 * </p>
 *
 * @author hehe
 * @since 2024-11-03
 */
@Mapper
public interface WorkersMapper extends BaseMapper<Workers> {

    String getWorkerName(@Param("workerId") Integer workerId);

    Workers getWorkerById(Integer workerId);
}
