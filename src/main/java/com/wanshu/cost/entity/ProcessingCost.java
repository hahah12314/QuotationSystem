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
 * 单件加工费用表
 * </p>
 *
 * @author hehe
 * @since 2024-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("processing_cost")
@ApiModel(value="ProcessingCost对象", description="单件加工费用表")
public class ProcessingCost implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "关联原材料表的唯一标识符")
    private Integer detailId;

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


}
