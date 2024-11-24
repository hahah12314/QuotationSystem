package com.wanshu.cost.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.cost.entity.RawMaterials;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 原材料表 服务类
 * </p>
 *
 * @author hehe
 * @since 2024-10-12
 */
public interface IRawMaterialsService extends IService<RawMaterials> {

    PageUtils queryPageRawMaterial(RawMaterialQueryDto rawMaterialQueryDto);

    Map<String, Object> getRawMaterialDetail(Long detailId);

    List<RawMaterials> queryAll();

    boolean saverawMaterials(RawMaterials rawMaterials);

    String deleteRawMaterials(int id);

    boolean updateRawMaterials(RawMaterials rawMaterials);
}
