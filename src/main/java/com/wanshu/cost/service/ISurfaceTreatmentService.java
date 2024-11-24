package com.wanshu.cost.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.cost.entity.SurfaceTreatment;

/**
 * <p>
 * 表面处理单件费用表 服务类
 * </p>
 *
 * @author hehe
 * @since 2024-10-14
 */
public interface ISurfaceTreatmentService extends IService<SurfaceTreatment> {

    PageUtils queryPageSufaceCost(RawMaterialQueryDto rawMaterialQueryDto);

    boolean saveSurfaceTreatment(SurfaceTreatment surfaceTreatment);

    String deleteSurfaceTreatment(int id);

    boolean updateSurfaceTreatment(SurfaceTreatment surfaceTreatment);
}
