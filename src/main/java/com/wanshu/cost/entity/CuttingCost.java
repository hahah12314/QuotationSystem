package com.wanshu.cost.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 数割费用表
 * </p>
 *
 * @author hehe
 * @since 2024-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cutting_cost")
public class CuttingCost implements Serializable {

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
     * 数割(M)
     */
    private Integer numCut;

    /**
     * 下料费
     */
    private BigDecimal cutFee;

    /**
     * 费用小计
     */
    private BigDecimal totalCutCost;

    @TableField(exist = false)
    private String specification;


}
