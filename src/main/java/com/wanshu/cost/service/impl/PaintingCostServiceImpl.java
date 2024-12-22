package com.wanshu.cost.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanshu.common.annotation.SystemLog;
import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.cost.entity.PaintingCost;
import com.wanshu.cost.entity.RawMaterials;
import com.wanshu.cost.mapper.PaintingCostMapper;
import com.wanshu.cost.mapper.ProcessingCostMapper;
import com.wanshu.cost.mapper.RawMaterialsMapper;
import com.wanshu.cost.service.IPaintingCostService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 喷涂单件费用表 服务实现类
 * </p>
 *
 * @author hehe
 * @since 2024-10-14
 */
@Service
public class PaintingCostServiceImpl extends ServiceImpl<PaintingCostMapper, PaintingCost> implements IPaintingCostService {

    @Autowired
    private ProcessingCostMapper processingCostMapper;
    @Autowired
    private RawMaterialsMapper rawMaterialsMapper;
    @Override
    public PageUtils queryPagePaintingCost(RawMaterialQueryDto rawMaterialQueryDto) {
        QueryWrapper<PaintingCost> wrapper=  new QueryWrapper<>();
        QueryWrapper<RawMaterials> wrapper2=  new QueryWrapper<>();
        wrapper2.like(StringUtils.isNotEmpty(rawMaterialQueryDto.getSpecification()), "specification", rawMaterialQueryDto.getSpecification());
        List<Integer> detailIds = rawMaterialsMapper.selectList(wrapper2).stream().map(RawMaterials::getDetailId).collect(Collectors.toList());
        wrapper.in("detail_id", detailIds);

        Page<PaintingCost> page = this.page(rawMaterialQueryDto.page(),wrapper);
        List<PaintingCost> records = page.getRecords();
        records.forEach(item->{
            item.setSpecification(rawMaterialsMapper.selectById(item.getDetailId()).getSpecification());
        });
        return new PageUtils(page);
    }

    @Override
    @SystemLog(value = "新增喷涂单件费用")
    public boolean savePaintingCost(PaintingCost paintingCost) {
        this.baseMapper.insert(paintingCost);
        return true;
    }

    @Override
    @Transactional
    @SystemLog(value = "删除喷涂单件费用")
    public String deletePaintingCost(int id) {
        this.baseMapper.deletePaintingCostById(id);
        return "success";
    }

    @Override
    @SystemLog(value = "更新喷涂单件费用")
    public boolean updatePaintingCost(PaintingCost paintingCost) {
        this.updateById(paintingCost);
        return true;
    }
}
