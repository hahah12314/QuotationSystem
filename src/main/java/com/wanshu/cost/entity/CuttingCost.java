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
 * 数割费用表
 * </p>
 *
 * @author hehe
 * @since 2024-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cutting_cost")
@ApiModel(value="CuttingCost对象", description="数割费用表")
public class CuttingCost implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "关联原材料表的唯一标识符")
    private Integer detailId;

    @ApiModelProperty(value = "数割(M)")
    private Integer numCut;

    @ApiModelProperty(value = "下料费")
    private BigDecimal cutFee;

    @ApiModelProperty(value = "费用小计")
    private BigDecimal totalCutFee;


}
