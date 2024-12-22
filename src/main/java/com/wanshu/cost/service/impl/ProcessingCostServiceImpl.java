package com.wanshu.cost.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanshu.common.annotation.SystemLog;
import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.cost.entity.ProcessingCost;
import com.wanshu.cost.entity.RawMaterials;
import com.wanshu.cost.mapper.ProcessingCostMapper;
import com.wanshu.cost.mapper.RawMaterialsMapper;
import com.wanshu.cost.service.IProcessingCostService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 单件加工费用表 服务实现类
 * </p>
 *
 * @author hehe
 * @since 2024-10-14
 */
@Service
public class ProcessingCostServiceImpl extends ServiceImpl<ProcessingCostMapper, ProcessingCost> implements IProcessingCostService {

    @Autowired
    private ProcessingCostMapper processingCostMapper;
    @Autowired
    private RawMaterialsMapper rawMaterialsMapper;
    @Override
    public PageUtils queryPageProcessCost(RawMaterialQueryDto rawMaterialQueryDto) {

        QueryWrapper<ProcessingCost> wrapper=  new QueryWrapper<>();
        QueryWrapper<RawMaterials> wrapper2=  new QueryWrapper<>();
        wrapper2.like(StringUtils.isNotEmpty(rawMaterialQueryDto.getSpecification()), "specification", rawMaterialQueryDto.getSpecification());
        List<Integer> detailIds = rawMaterialsMapper.selectList(wrapper2).stream().map(RawMaterials::getDetailId).collect(Collectors.toList());
        wrapper.in("detail_id", detailIds);

        Page<ProcessingCost> page = this.page(rawMaterialQueryDto.page(),wrapper);
        List<ProcessingCost> records = page.getRecords();
        records.forEach(item->{
            item.setSpecification(rawMaterialsMapper.selectById(item.getDetailId()).getSpecification());
        });
        return new PageUtils(page);


    }

    @Override
    @SystemLog(value = "新增单件加工费用")
    public boolean saveProcessingCost(ProcessingCost processingCost) {
        this.baseMapper.insert(processingCost);
        return true;
    }

    @Override
    @Transactional
    @SystemLog(value = "删除单件加工费用")
    public String deleteProcessingCost(int id) {
        this.baseMapper.deleteProcessingCostById(id);
        return "success";
    }

    @Override
    @SystemLog(value = "更新单件加工费用")
    public boolean updateProcessingCost(ProcessingCost processingCost) {
        this.updateById(processingCost);
        return true;
    }
}
