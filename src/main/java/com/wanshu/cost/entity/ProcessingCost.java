package com.wanshu.cost.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 单件加工费用表
 * </p>
 *
 * @author hehe
 * @since 2024-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("processing_cost")
public class ProcessingCost implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关联原材料表的唯一标识符
     */
    private Integer detailId;

    /**
     * 锯工时
     */
    private BigDecimal sawTime;

    /**
     * 关联锯的工时id
     */
    private Integer sawWorkhourId;

    /**
     * 锯金额
     */
    private BigDecimal sawFee;

    /**
     * 弯工时
     */
    private BigDecimal bendTime;

    /**
     * 弯金额
     */
    private BigDecimal bendFee;

    private Integer bendWorkhourId;

    /**
     * 钻工时
     */
    private BigDecimal drillTime;

    /**
     * 钻金额
     */
    private BigDecimal drillFee;

    private Integer drillWorkhourId;

    private Integer turnWorkhourId;

    /**
     * 车工时
     */
    private BigDecimal turnTime;

    /**
     * 车金额
     */
    private BigDecimal turnFee;

    /**
     * 外磨工时
     */
    private BigDecimal grindTime;

    /**
     * 外磨金额
     */
    private BigDecimal grindFee;

    private Integer grindWorkhourId;

    /**
     * 铣工时
     */
    private BigDecimal millTime;

    /**
     * 铣金额
     */
    private BigDecimal millFee;

    private Integer millWorkhourId;

    /**
     * 校平工时
     */
    private BigDecimal calibrateTime;

    /**
     * 校平金额
     */
    private BigDecimal calibrateFee;

    private Integer calibrateWorkhourId;

    /**
     * 镗铣工时
     */
    private BigDecimal boreMillTime;

    private Integer boreMillWorkhourId;

    /**
     * 镗铣金额
     */
    private BigDecimal boreMillFee;

    /**
     * 焊工时
     */
    private BigDecimal weldTime;

    private Integer weldWorkhourId;

    /**
     * 焊金额
     */
    private BigDecimal weldFee;

    /**
     * 打磨工时
     */
    private BigDecimal polishTime;

    /**
     * 打磨金额
     */
    private BigDecimal polishFee;

    private Integer polishWorkhourId;

    /**
     * 装工时
     */
    private BigDecimal assembleTime;

    /**
     * 装金额
     */
    private BigDecimal assembleFee;

    /**
     * 费用小计
     */
    private BigDecimal totalProcessCost;

    private Integer assembleWorkhourId;

    @TableField(exist = false)
    private String specification;


}
