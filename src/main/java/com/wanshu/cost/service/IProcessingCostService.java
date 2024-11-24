package com.wanshu.cost.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.cost.entity.ProcessingCost;

/**
 * <p>
 * 单件加工费用表 服务类
 * </p>
 *
 * @author hehe
 * @since 2024-10-14
 */
public interface IProcessingCostService extends IService<ProcessingCost> {

    PageUtils queryPageProcessCost(RawMaterialQueryDto rawMaterialQueryDto);

    boolean saveProcessingCost(ProcessingCost processingCost);

    String deleteProcessingCost(int id);

    boolean updateProcessingCost(ProcessingCost processingCost);
}
