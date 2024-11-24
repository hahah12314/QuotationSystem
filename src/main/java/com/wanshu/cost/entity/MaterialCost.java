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
 * 原材料费用表
 * </p>
 *
 * @author hehe
 * @since 2024-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("material_cost")
public class MaterialCost implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识符
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关联原材料表的唯一标识符
     */
    private Integer detailId;

    /**
     * 长/直径/外径
     */
    private BigDecimal length;

    /**
     * 宽/直径/壁厚
     */
    private BigDecimal width;

    /**
     * 高/长/长
     */
    private BigDecimal height;

    /**
     * 毛坯重量(kg)
     */
    private BigDecimal blankWeight;

    /**
     * 材料单价(元/kg)
     */
    private BigDecimal materialUnitPrice;

    /**
     * 毛坯费(元)
     */
    private BigDecimal blankFee;

    /**
     * 废料重
     */
    private BigDecimal scrapWeight;

    /**
     * 废料单价(元/kg)
     */
    private BigDecimal scrapUnitPrice;

    /**
     * 废料费(元)
     */
    private BigDecimal scrapFee;

    /**
     * 原材料费用小计
     */
    private BigDecimal totalMaterialCost;
    @TableField(exist = false)
    private String specification;


}
