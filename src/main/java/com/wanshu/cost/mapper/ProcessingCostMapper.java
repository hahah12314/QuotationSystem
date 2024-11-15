package com.wanshu.cost.mapper;

import com.wanshu.cost.entity.ProcessingCost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 单件加工费用表 Mapper 接口
 * </p>
 *
 * @author hehe
 * @since 2024-11-12
 */
public interface ProcessingCostMapper extends BaseMapper<ProcessingCost> {

    ProcessingCost selectByDetailId(Integer detailId);
}
