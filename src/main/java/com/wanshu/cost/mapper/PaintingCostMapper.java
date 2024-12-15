package com.wanshu.cost.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanshu.cost.entity.PaintingCost;

import java.util.List;

/**
 * <p>
 * 喷涂单件费用表 Mapper 接口
 * </p>
 *
 * @author hehe
 * @since 2024-11-12
 */
public interface PaintingCostMapper extends BaseMapper<PaintingCost> {

    List<PaintingCost> selectByDetailId(Integer detailId);

    void deletePaintingCostById(int id);
}
