package com.wanshu.cost.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanshu.cost.entity.MaterialCost;

import java.util.List;

/**
 * <p>
 * 原材料费用表 Mapper 接口
 * </p>
 *
 * @author hehe
 * @since 2024-11-12
 */
public interface MaterialCostMapper extends BaseMapper<MaterialCost> {

    List<MaterialCost> selectByDetailId(Integer detailId);

    void deleteMaterialCostById(int id);
}
