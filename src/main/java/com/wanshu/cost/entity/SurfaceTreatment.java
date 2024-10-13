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
 * 表面处理单件费用表
 * </p>
 *
 * @author hehe
 * @since 2024-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("surface_treatment")
@ApiModel(value="SurfaceTreatment对象", description="表面处理单件费用表")
public class SurfaceTreatment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "关联原材料表的唯一标识符")
    private Integer detailId;

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
