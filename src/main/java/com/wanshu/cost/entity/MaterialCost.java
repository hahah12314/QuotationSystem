package com.wanshu.cost.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 原材料费用表
 * </p>
 *
 * @author hehe
 * @since 2024-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("material_cost")
@ApiModel(value="MaterialCost对象", description="原材料费用表")
public class MaterialCost implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一标识符")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "关联原材料表的唯一标识符")
    private Integer detailId;

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


}
