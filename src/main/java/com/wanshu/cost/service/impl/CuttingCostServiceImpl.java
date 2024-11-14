package com.wanshu.cost.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.cost.entity.CuttingCost;
import com.wanshu.cost.entity.RawMaterials;
import com.wanshu.cost.mapper.CuttingCostMapper;
import com.wanshu.cost.mapper.RawMaterialsMapper;
import com.wanshu.cost.service.ICuttingCostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 数割费用表 服务实现类
 * </p>
 *
 * @author hehe
 * @since 2024-10-14
 */
@Service
public class CuttingCostServiceImpl extends ServiceImpl<CuttingCostMapper,CuttingCost> implements ICuttingCostService {
    @Autowired
    private CuttingCostMapper cuttingCostMapper;
    @Autowired
    private RawMaterialsMapper rawMaterialsMapper;
    @Override
    public PageUtils queryPageCuttingCost(RawMaterialQueryDto rawMaterialQueryDto) {
        QueryWrapper<CuttingCost> wrapper=  new QueryWrapper<>();
        QueryWrapper<RawMaterials> wrapper2=  new QueryWrapper<>();
        wrapper2.like(StringUtils.isNotEmpty(rawMaterialQueryDto.getSpecification()), "specification", rawMaterialQueryDto.getSpecification());
        List<Integer> detailIds = rawMaterialsMapper.selectList(wrapper2).stream().map(RawMaterials::getDetailId).collect(Collectors.toList());
        wrapper.in("detail_id", detailIds);

        Page<CuttingCost> page = this.page(rawMaterialQueryDto.page(),wrapper);
        List<CuttingCost> records = page.getRecords();
        records.forEach(item->{
            item.setSpecification(rawMaterialsMapper.selectById(item.getDetailId()).getSpecification());
        });
        return new PageUtils(page);
    }
}
