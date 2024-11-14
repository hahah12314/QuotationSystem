package com.wanshu.cost.service;

import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanshu.cost.entity.MaterialCost;

/**
 * <p>
 * 原材料费用表 服务类
 * </p>
 *
 * @author hehe
 * @since 2024-10-14
 */
public interface IMaterialCostService extends IService<MaterialCost> {

    PageUtils queryPageMaterialCost(RawMaterialQueryDto rawMaterialQueryDto);
}
