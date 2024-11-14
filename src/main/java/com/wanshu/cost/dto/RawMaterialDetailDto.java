package com.wanshu.cost.dto;

import com.wanshu.cost.entity.RawMaterials;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterialDetailDto extends RawMaterials {
    @ApiModelProperty(value = "数割(M)")
    private Integer numCut;

    @ApiModelProperty(value = "下料费")
    private BigDecimal cutFee;

    @ApiModelProperty(value = "费用小计")
    private BigDecimal totalCutFee;
    @ApiModelProperty(value = "长/直径/外径")
    private BigDecimal length;

    @ApiModelProperty(value = "宽/直径/壁厚")
    private BigDecimal width;

    @ApiModelProperty(value = "高/长/长")
    private BigDecimal height;

    @ApiModelProperty(value = "毛坯重量(kg)")
    private BigDecimal blankWeight;

    @ApiModelProperty(value = "材料单价(元/kg)")
    private BigDecimal materialUnitPrice;

    @ApiModelProperty(value = "毛坯费(元)")
    private BigDecimal blankFee;

    @ApiModelProperty(value = "废料重")
    private BigDecimal scrapWeight;

    @ApiModelProperty(value = "废料单价(元/kg)")
    private BigDecimal scrapUnitPrice;

    @ApiModelProperty(value = "废料费(元)")
    private BigDecimal scrapFee;

    @ApiModelProperty(value = "原材料费用小计")
    private BigDecimal totalMaterialCost;
    @ApiModelProperty(value = "喷砂工时")
    private BigDecimal sandblastTime;

    @ApiModelProperty(value = "喷砂金额")
    private BigDecimal sandblastFee;

    @ApiModelProperty(value = "喷漆工时")
    private BigDecimal paintTime;

    @ApiModelProperty(value = "喷漆金额")
    private BigDecimal paintFee;

    @ApiModelProperty(value = "喷塑工时")
    private BigDecimal plasticTime;

    @ApiModelProperty(value = "喷塑金额")
    private BigDecimal plasticFee;

    @ApiModelProperty(value = "费用小计")
    private BigDecimal totalPaintingFee;
    @ApiModelProperty(value = "锯工时")
    private BigDecimal sawTime;

    @ApiModelProperty(value = "锯金额")
    private BigDecimal sawFee;

    @ApiModelProperty(value = "弯工时")
    private BigDecimal bendTime;

    @ApiModelProperty(value = "弯金额")
    private BigDecimal bendFee;

    @ApiModelProperty(value = "钻工时")
    private BigDecimal drillTime;

    @ApiModelProperty(value = "钻金额")
    private BigDecimal drillFee;

    @ApiModelProperty(value = "车工时")
    private BigDecimal turnTime;

    @ApiModelProperty(value = "车金额")
    private BigDecimal turnFee;

    @ApiModelProperty(value = "外磨工时")
    private BigDecimal grindTime;

    @ApiModelProperty(value = "外磨金额")
    private BigDecimal grindFee;

    @ApiModelProperty(value = "铣工时")
    private BigDecimal millTime;

    @ApiModelProperty(value = "铣金额")
    private BigDecimal millFee;

    @ApiModelProperty(value = "校平工时")
    private BigDecimal calibrateTime;

    @ApiModelProperty(value = "校平金额")
    private BigDecimal calibrateFee;

    @ApiModelProperty(value = "镗铣工时")
    private BigDecimal boreMillTime;

    @ApiModelProperty(value = "镗铣金额")
    private BigDecimal boreMillFee;

    @ApiModelProperty(value = "焊工时")
    private BigDecimal weldTime;

    @ApiModelProperty(value = "焊金额")
    private BigDecimal weldFee;

    @ApiModelProperty(value = "打磨工时")
    private BigDecimal polishTime;

    @ApiModelProperty(value = "打磨金额")
    private BigDecimal polishFee;

    @ApiModelProperty(value = "装工时")
    private BigDecimal assembleTime;

    @ApiModelProperty(value = "装金额")
    private BigDecimal assembleFee;

    @ApiModelProperty(value = "费用小计")
    private BigDecimal totalProcessFee;
    @ApiModelProperty(value = "镀锌工时")
    private BigDecimal zincWeight;

    @ApiModelProperty(value = "镀锌金额")
    private BigDecimal zincFee;

    @ApiModelProperty(value = "调质工时")
    private BigDecimal temperingWeight;

    @ApiModelProperty(value = "调质金额")
    private BigDecimal temperingFee;

    @ApiModelProperty(value = "冲砂工时")
    private BigDecimal sandWeight;

    @ApiModelProperty(value = "冲砂金额")
    private BigDecimal sandFee;

    @ApiModelProperty(value = "QPQ工时")
    private BigDecimal qpqWeight;

    @ApiModelProperty(value = "QPQ金额")
    private BigDecimal qpqFee;

    @ApiModelProperty(value = "费用小计")
    private BigDecimal totalCoatingFee;

}
