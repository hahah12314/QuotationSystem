package com.wanshu.cost.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanshu.cost.entity.ProcessingCost;

import java.util.List;

/**
 * <p>
 * 单件加工费用表 Mapper 接口
 * </p>
 *
 * @author hehe
 * @since 2024-11-12
 */
public interface ProcessingCostMapper extends BaseMapper<ProcessingCost> {

    List<ProcessingCost> selectByDetailId(Integer detailId);

    void deleteProcessingCostById(int id);
}
