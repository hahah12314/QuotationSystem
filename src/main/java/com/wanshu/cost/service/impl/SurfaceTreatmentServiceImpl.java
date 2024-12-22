package com.wanshu.cost.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanshu.common.annotation.SystemLog;
import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.cost.entity.RawMaterials;
import com.wanshu.cost.entity.SurfaceTreatment;
import com.wanshu.cost.mapper.RawMaterialsMapper;
import com.wanshu.cost.mapper.SurfaceTreatmentMapper;
import com.wanshu.cost.service.ISurfaceTreatmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 表面处理单件费用表 服务实现类
 * </p>
 *
 * @author hehe
 * @since 2024-10-14
 */
@Service
public class SurfaceTreatmentServiceImpl extends ServiceImpl<SurfaceTreatmentMapper, SurfaceTreatment> implements ISurfaceTreatmentService {

    @Autowired
    private SurfaceTreatmentMapper surfaceTreatmentMapper;
    @Autowired
    private RawMaterialsMapper rawMaterialsMapper;
    @Override
    public PageUtils queryPageSufaceCost(RawMaterialQueryDto rawMaterialQueryDto) {
        QueryWrapper<SurfaceTreatment> wrapper=  new QueryWrapper<>();
        QueryWrapper<RawMaterials> wrapper2=  new QueryWrapper<>();
        wrapper2.like(StringUtils.isNotEmpty(rawMaterialQueryDto.getSpecification()), "specification", rawMaterialQueryDto.getSpecification());
        List<Integer> detailIds = rawMaterialsMapper.selectList(wrapper2).stream().map(RawMaterials::getDetailId).collect(Collectors.toList());
        wrapper.in("detail_id", detailIds);

        Page<SurfaceTreatment> page = this.page(rawMaterialQueryDto.page(),wrapper);
        List<SurfaceTreatment> records = page.getRecords();
        records.forEach(item->{
            item.setSpecification(rawMaterialsMapper.selectById(item.getDetailId()).getSpecification());
        });
        return new PageUtils(page);
    }

    @Override
    @SystemLog(value = "新增表面处理单件费用")
    public boolean saveSurfaceTreatment(SurfaceTreatment surfaceTreatment) {
        this.baseMapper.insert(surfaceTreatment);
        return true;
    }

    @Override
    @Transactional
    @SystemLog(value = "删除表面处理单件费用")
    public String deleteSurfaceTreatment(int id) {
        this.baseMapper.deleteSurfaceTreatmentCostById(id);
        return "success";
    }

    @Override
    @SystemLog(value = "更新表面处理单件费用")
    public boolean updateSurfaceTreatment(SurfaceTreatment surfaceTreatment) {
        this.updateById(surfaceTreatment);
        return true;
    }
}
