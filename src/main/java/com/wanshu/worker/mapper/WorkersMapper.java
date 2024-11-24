package com.wanshu.worker.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanshu.worker.entity.Workers;
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


    void delete(Integer id);


    void deleteWorkerById(Integer id);
}
