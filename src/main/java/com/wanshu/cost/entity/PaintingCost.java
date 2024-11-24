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
 * 喷涂单件费用表
 * </p>
 *
 * @author hehe
 * @since 2024-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("painting_cost")
public class PaintingCost implements Serializable {

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
     * 酸洗/磷化单价
     */
    private BigDecimal acidWashCost;

    /**
     * 酸洗/磷化面积
     */
    private BigDecimal acidWashWeight;

    /**
     * 喷塑单价
     */
    private BigDecimal sprayPlasticCost;

    /**
     * 底漆单价
     */
    private BigDecimal primerCost;

    /**
     * 底漆面积
     */
    private BigDecimal primerArea;

    /**
     * 喷塑面积
     */
    private BigDecimal sprayPlasticArea;

    /**
     * 电泳单价
     */
    private BigDecimal electroswimmingCost;

    /**
     * 电泳面积
     */
    private BigDecimal electroswimmingArea;

    /**
     * 费用小计
     */
    private BigDecimal totalPaintingCost;

    /**
     * 面漆单价
     */
    private BigDecimal topcoatCost;

    /**
     * 面漆面积
     */
    private BigDecimal topcoatArea;

    @TableField(exist = false)
    private String specification;
}
