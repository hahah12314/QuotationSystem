package com.wanshu.cost.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 喷涂单件费用表
 * </p>
 *
 * @author hehe
 * @since 2024-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("painting_cost")
@ApiModel(value="PaintingCost对象", description="喷涂单件费用表")
public class PaintingCost implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "关联原材料表的唯一标识符")
    @TableField("detail_id")
    private Integer detailId;

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


}
