package com.wanshu.cost.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanshu.cost.entity.Materials;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 物料表 Mapper 接口
 * </p>
 *
 * @author hehe
 * @since 2024-10-12
 */

@Mapper
public interface MaterialsMapper extends BaseMapper<Materials> {

    void deleteMaterialsByMaterialId(int materialId);
}
