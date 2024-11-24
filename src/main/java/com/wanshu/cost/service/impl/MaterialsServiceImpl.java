package com.wanshu.cost.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanshu.common.annotation.SystemLog;
import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.MaterialQueryDto;
import com.wanshu.cost.entity.Materials;
import com.wanshu.cost.mapper.MaterialsMapper;
import com.wanshu.cost.service.IMaterialsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
        wrapper.or().like(StringUtils.isNotEmpty(materialQueryDto.getMaterialName()), "material_code", materialQueryDto.getMaterialName());
        Page<Materials> page = this.page(materialQueryDto.page(),wrapper);
        return new PageUtils(page);
    }

    @Override
    public List<Materials> queryAll() {
        QueryWrapper<Materials> wrapper = new QueryWrapper<>();
        return materialsMapper.selectList(wrapper);
    }

    @Override
    public boolean saveMaterials(Materials materials) {
        materials.setCreateTime(LocalDateTime.now());
        materials.setUpdateTime(LocalDateTime.now());
        this.baseMapper.insert(materials);
        return true;
    }

    @Override
    @Transactional
    @SystemLog(value = "删除物料")
    //这个表主键是material_id
    public String deleteMaterials(int id) {
        this.baseMapper.deleteMaterialsByMaterialId(id);
        return "success";
    }

    @Override
    //这个写id不是materialId能改成功吗
    public boolean updateMaterials(Materials materials) {
        materials.setUpdateTime(LocalDateTime.now());
        this.updateById(materials);
        return true;
    }


}
