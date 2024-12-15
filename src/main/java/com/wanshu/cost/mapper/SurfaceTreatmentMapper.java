package com.wanshu.cost.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanshu.cost.entity.SurfaceTreatment;

import java.util.List;

/**
 * <p>
 * 表面处理单件费用表 Mapper 接口
 * </p>
 *
 * @author hehe
 * @since 2024-11-12
 */
public interface SurfaceTreatmentMapper extends BaseMapper<SurfaceTreatment> {

    List<SurfaceTreatment> selectByDetailId(Integer detailId);

    void deleteSurfaceTreatmentCostById(int id);
}
