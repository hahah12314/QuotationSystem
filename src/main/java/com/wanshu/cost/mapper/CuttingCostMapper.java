package com.wanshu.cost.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanshu.cost.entity.CuttingCost;

import java.util.List;

/**
 * <p>
 * 数割费用表 Mapper 接口
 * </p>
 *
 * @author hehe
 * @since 2024-11-12
 */
public interface CuttingCostMapper extends BaseMapper<CuttingCost> {

    List<CuttingCost> selectByDetailId(Integer detailId);

    void deleteCuttingCostById(int id);
}
