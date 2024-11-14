package com.wanshu.cost.service;

import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.MaterialQueryDto;
import com.wanshu.cost.entity.Materials;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 物料表 服务类
 * </p>
 *
 * @author hehe
 * @since 2024-10-12
 */
public interface IMaterialsService extends IService<Materials> {

    PageUtils queryPageMaterial(MaterialQueryDto materialQueryDto);


    List<Materials> queryAll();
}
