package com.wanshu.cost.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.MaterialQueryDto;
import com.wanshu.cost.entity.Materials;
import com.wanshu.cost.mapper.MaterialsMapper;
import com.wanshu.cost.service.IMaterialsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanshu.sys.entity.SysRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 物料表 服务实现类
 * </p>
 *
 * @author hehe
 * @since 2024-10-12
 */
@Service
public class MaterialsServiceImpl extends ServiceImpl<MaterialsMapper, Materials> implements IMaterialsService {

    @Autowired
    private MaterialsMapper materialsMapper;
    @Override
    public PageUtils queryPageMaterial(MaterialQueryDto materialQueryDto) {
        QueryWrapper<Materials> wrapper=  new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(materialQueryDto.getMaterialName()), "material_name", materialQueryDto.getMaterialName());
        Page<Materials> page = this.page(materialQueryDto.page(),wrapper);
        return new PageUtils(page);
    }
}
