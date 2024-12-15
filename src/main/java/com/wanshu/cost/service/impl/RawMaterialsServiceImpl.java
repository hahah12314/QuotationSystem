package com.wanshu.cost.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanshu.common.annotation.SystemLog;
import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.cost.entity.*;
import com.wanshu.cost.mapper.*;
import com.wanshu.cost.service.IRawMaterialsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 原材料表 服务实现类
 * </p>
 *
 * @author hehe
 * @since 2024-10-12
 */
@Service
public class RawMaterialsServiceImpl extends ServiceImpl<RawMaterialsMapper, RawMaterials> implements IRawMaterialsService {

    @Autowired
    private PaintingCostMapper paintingCostMapper;
    @Autowired
    private RawMaterialsMapper rawMaterialsMapper;
    @Autowired
    private ProcessingCostMapper processingCostMapper;
    @Autowired
    private SurfaceTreatmentMapper surfaceTreatmentMapper;
    @Autowired
    private CuttingCostMapper cuttingCostMapper;
    @Autowired
    private MaterialCostMapper materialCostMapper;
    @Override
    public PageUtils queryPageRawMaterial(RawMaterialQueryDto rawMaterialQueryDto) {
        // 创建分页对象
        Page<RawMaterials> page = rawMaterialQueryDto.page();

        // 构建查询条件
        QueryWrapper<RawMaterials> wrapper = new QueryWrapper<>();
        if (StringUtils.isNoneEmpty(rawMaterialQueryDto.getSpecification())) {
            wrapper.like("specification", rawMaterialQueryDto.getSpecification());
        }

        // 执行分页查询
        Page<RawMaterials> materialCostPage = rawMaterialsMapper.selectPage(page, wrapper);

        return new PageUtils(materialCostPage);
    }

    @Override
    public Map<String, Object> getRawMaterialDetail(Long detailId)  {

        RawMaterials rawMaterials = rawMaterialsMapper.selectOne(
                new QueryWrapper<RawMaterials>()
                        .eq("detail_id", detailId)
        );

// 查询各个表中的数据并获取最新的记录
        List<MaterialCost> materialCostList = materialCostMapper.selectList(
                new QueryWrapper<MaterialCost>()
                        .eq("detail_id", detailId)

        );
        MaterialCost materialCost = materialCostList != null && !materialCostList.isEmpty() ? materialCostList.get(materialCostList.size() - 1) : null;

        List<PaintingCost> paintingCostList = paintingCostMapper.selectList(
                new QueryWrapper<PaintingCost>()
                        .eq("detail_id", detailId)

        );
        PaintingCost paintingCost = paintingCostList != null && !paintingCostList.isEmpty() ? paintingCostList.get(paintingCostList.size() - 1) : null;

        List<CuttingCost> cuttingCostList = cuttingCostMapper.selectList(
                new QueryWrapper<CuttingCost>()
                        .eq("detail_id", detailId)

        );
        CuttingCost cuttingCost = cuttingCostList != null && !cuttingCostList.isEmpty() ? cuttingCostList.get(cuttingCostList.size() - 1) : null;

        List<ProcessingCost> processingCostList = processingCostMapper.selectList(
                new QueryWrapper<ProcessingCost>()
                        .eq("detail_id", detailId)

        );
        ProcessingCost processingCost = processingCostList != null && !processingCostList.isEmpty() ? processingCostList.get(processingCostList.size() - 1) : null;

        List<SurfaceTreatment> surfaceTreatmentList = surfaceTreatmentMapper.selectList(
                new QueryWrapper<SurfaceTreatment>()
                        .eq("detail_id", detailId)

        );
        SurfaceTreatment surfaceTreatment = surfaceTreatmentList != null && !surfaceTreatmentList.isEmpty() ? surfaceTreatmentList.get(surfaceTreatmentList.size() - 1) : null;

        // 创建返回的Map
        Map<String, Object> result = new HashMap<>();
        result.put("rawMaterials", rawMaterials);
        result.put("materialCost", materialCost);
        result.put("paintingCost", paintingCost);
        result.put("cuttingCost", cuttingCost);
        result.put("processingCost", processingCost);
        result.put("surfaceTreatment", surfaceTreatment);

        // 返回Map
        System.out.println(result);
        return result;


    }

    @Override
    public List<RawMaterials> queryAll() {
        QueryWrapper<RawMaterials> queryWrapper = new QueryWrapper<>();
        return rawMaterialsMapper.selectList(queryWrapper);
    }

    @Override
    public boolean saverawMaterials(RawMaterials rawMaterials) {
        rawMaterials.setCreateTime(LocalDateTime.now());
        rawMaterials.setUpdateTime(LocalDateTime.now());
        this.baseMapper.insert(rawMaterials);
        return true;
    }

    @Override
    @Transactional
    @SystemLog(value = "删除原材料")
    //这个表主键是detailed_id
    public String deleteRawMaterials(int id) {
        this.baseMapper.deleteRawMaterialsByDetailId(id);
        return "success";
    }

    @Override
    public boolean updateRawMaterials(RawMaterials rawMaterials) {
        rawMaterials.setUpdateTime(LocalDateTime.now());
        this.updateById(rawMaterials);
        return true;
    }
}
