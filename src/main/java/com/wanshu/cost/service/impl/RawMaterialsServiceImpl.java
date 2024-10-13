package com.wanshu.cost.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.cost.entity.*;
import com.wanshu.cost.mapper.*;
import com.wanshu.cost.service.IRawMaterialsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanshu.cost.vo.RawMaterialQueryVo;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        // 获取 detailId 列表
        List<Integer> detailIds = materialCostPage.getRecords().stream()
                .map(RawMaterials::getDetailId)
                .collect(Collectors.toList());
        List<MaterialCost> materialCostList = materialCostMapper.selectList(
                new QueryWrapper<MaterialCost>()
                        .in("detail_id", detailIds)
        );

        // 查询喷涂单件费用表中的数据
        List<PaintingCost> paintingList = paintingCostMapper.selectList(
                new QueryWrapper<PaintingCost>()
                        .in("detail_id", detailIds)
        );
        List<CuttingCost> cuttingList = cuttingCostMapper.selectList(
                new QueryWrapper<CuttingCost>()
                        .in("detail_id", detailIds)
        );
        List<ProcessingCost> processingList = processingCostMapper.selectList(
                new QueryWrapper<ProcessingCost>()
                        .in("detail_id", detailIds)
        );
        List<SurfaceTreatment> surfaceList = surfaceTreatmentMapper.selectList(
                new QueryWrapper<SurfaceTreatment>()
                        .in("detail_id", detailIds)
        );

        // 将查询结果合并到 RawMaterialQueryVo 中
        List<RawMaterialQueryVo> rawMaterialQueryVos = materialCostPage.getRecords().stream()
                .map(materialCost -> {
                    RawMaterialQueryVo rawMaterialQueryVo = new RawMaterialQueryVo();
                    BeanUtils.copyProperties(materialCost, rawMaterialQueryVo);
                    MaterialCost material = materialCostList.stream()
                            .filter(m -> m.getDetailId().equals(materialCost.getDetailId()))
                            .findFirst()
                            .orElse(null);

                    PaintingCost painting = paintingList.stream()
                            .filter(p -> p.getDetailId().equals(materialCost.getDetailId()))
                            .findFirst()
                            .orElse(null);
                    CuttingCost cutting = cuttingList.stream()
                            .filter(c -> c.getDetailId().equals(materialCost.getDetailId()))
                            .findFirst()
                            .orElse(null);
                    ProcessingCost processing = processingList.stream()
                            .filter(p -> p.getDetailId().equals(materialCost.getDetailId()))
                            .findFirst()
                            .orElse(null);
                    SurfaceTreatment surface = surfaceList.stream()
                            .filter(s -> s.getDetailId().equals(materialCost.getDetailId()))
                            .findFirst()
                            .orElse(null);
                    if(material != null){
                        rawMaterialQueryVo.setRaw_length(material.getLength());
                        rawMaterialQueryVo.setWidth(material.getWidth());
                        rawMaterialQueryVo.setHeight(material.getHeight());
                        rawMaterialQueryVo.setBlankWeight(material.getBlankWeight());
                        rawMaterialQueryVo.setMaterialUnitPrice(material.getMaterialUnitPrice());
                        rawMaterialQueryVo.setBlankFee(material.getBlankFee());
                        rawMaterialQueryVo.setScrapWeight(material.getScrapWeight());
                        rawMaterialQueryVo.setScrapUnitPrice(material.getScrapUnitPrice());
                        rawMaterialQueryVo.setScrapFee(material.getScrapFee());
                        rawMaterialQueryVo.setTotalMaterialCost(material.getTotalMaterialCost());
                    }


                    if (painting != null) {
                        rawMaterialQueryVo.setSandblastTime(painting.getSandblastTime());
                        rawMaterialQueryVo.setSandblastFee(painting.getSandblastFee());
                        rawMaterialQueryVo.setPaintTime(painting.getPaintTime());
                        rawMaterialQueryVo.setPaintFee(painting.getPaintFee());
                        rawMaterialQueryVo.setPlasticTime(painting.getPlasticTime());
                        rawMaterialQueryVo.setPlasticFee(painting.getPlasticFee());
                        rawMaterialQueryVo.setTotalPaintingFee(painting.getTotalPaintingFee());
                    }
                    if (cutting != null) {
                        rawMaterialQueryVo.setNumCut(cutting.getNumCut());
                        rawMaterialQueryVo.setCutFee(cutting.getCutFee());
                        rawMaterialQueryVo.setTotalCutFee(cutting.getTotalCutFee());
                    }
                    if(processing != null){
                        rawMaterialQueryVo.setSawTime(processing.getSawTime());
                        rawMaterialQueryVo.setSawFee(processing.getSawFee());
                        rawMaterialQueryVo.setBendTime(processing.getBendTime());
                        rawMaterialQueryVo.setBendFee(processing.getBendFee());
                        rawMaterialQueryVo.setDrillTime(processing.getDrillTime());
                        rawMaterialQueryVo.setDrillFee(processing.getDrillFee());
                        rawMaterialQueryVo.setTurnTime(processing.getTurnTime());
                        rawMaterialQueryVo.setTurnFee(processing.getTurnFee());
                        rawMaterialQueryVo.setGrindTime(processing.getGrindTime());
                        rawMaterialQueryVo.setGrindFee(processing.getGrindFee());
                        rawMaterialQueryVo.setMillTime(processing.getMillTime());
                        rawMaterialQueryVo.setMillFee(processing.getMillFee());
                        rawMaterialQueryVo.setCalibrateTime(processing.getCalibrateTime());
                        rawMaterialQueryVo.setCalibrateFee(processing.getCalibrateFee());
                        rawMaterialQueryVo.setBoreMillTime(processing.getBoreMillTime());
                        rawMaterialQueryVo.setBoreMillFee(processing.getBoreMillFee());
                        rawMaterialQueryVo.setWeldTime(processing.getWeldTime());
                        rawMaterialQueryVo.setWeldFee(processing.getWeldFee());
                        rawMaterialQueryVo.setPolishTime(processing.getPolishTime());
                        rawMaterialQueryVo.setPolishFee(processing.getPolishFee());
                        rawMaterialQueryVo.setAssembleTime(processing.getAssembleTime());
                        rawMaterialQueryVo.setAssembleFee(processing.getAssembleFee());
                        rawMaterialQueryVo.setTotalProcessFee(processing.getTotalProcessFee());


                    }
                    if(surface != null){
                        rawMaterialQueryVo.setZincWeight(surface.getZincWeight());
                        rawMaterialQueryVo.setZincFee(surface.getZincFee());
                        rawMaterialQueryVo.setTemperingWeight(surface.getTemperingWeight());
                        rawMaterialQueryVo.setTemperingFee(surface.getTemperingFee());
                        rawMaterialQueryVo.setSandWeight(surface.getSandWeight());
                        rawMaterialQueryVo.setSandFee(surface.getSandFee());
                        rawMaterialQueryVo.setQpqWeight(surface.getQpqWeight());
                        rawMaterialQueryVo.setQpqFee(surface.getQpqFee());
                        rawMaterialQueryVo.setTotalCoatingFee(surface.getTotalCoatingFee());

                    }


                    return rawMaterialQueryVo;
                })
                .collect(Collectors.toList());

        // 设置分页信息
        Page<RawMaterialQueryVo> rawMaterialPage = new Page<>(materialCostPage.getCurrent(), materialCostPage.getSize(), materialCostPage.getTotal());
        rawMaterialPage.setRecords(rawMaterialQueryVos);

        System.out.println(rawMaterialPage);
        return new PageUtils(rawMaterialPage);
    }
}
