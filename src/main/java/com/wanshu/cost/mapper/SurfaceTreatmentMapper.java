package com.wanshu.cost.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanshu.cost.entity.SurfaceTreatment;

/**
 * <p>
 * 表面处理单件费用表 Mapper 接口
 * </p>
 *
 * @author hehe
 * @since 2024-11-12
 */
public interface SurfaceTreatmentMapper extends BaseMapper<SurfaceTreatment> {

    SurfaceTreatment selectByDetailId(Integer detailId);

    void deleteSurfaceTreatmentCostById(int id);
}
