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
 * 表面处理单件费用表
 * </p>
 *
 * @author hehe
 * @since 2024-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("surface_treatment")
public class SurfaceTreatment implements Serializable {

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
     * 镀锌工时
     */
    private BigDecimal zincWeight;

    /**
     * 镀锌金额
     */
    private BigDecimal zincFee;

    /**
     * 调质工时
     */
    private BigDecimal temperingWeight;

    /**
     * 调质金额
     */
    private BigDecimal temperingFee;

    /**
     * 冲砂工时
     */
    private BigDecimal sandWeight;

    /**
     * 冲砂金额
     */
    private BigDecimal sandFee;

    /**
     * QPQ工时
     */
    private BigDecimal qpqWeight;

    /**
     * QPQ金额
     */
    private BigDecimal qpqFee;

    /**
     * 费用小计
     */
    private BigDecimal totalCoatingCost;

    /**
     *
     * 镀镍面积
     */
    private BigDecimal nickArea;
    /**
     * 镀镍厚度
     */
    private BigDecimal nickFee;
    /**
     * 镀铬面积
     */
    private BigDecimal chromeArea;
    /**
     * 镀铬厚度
     */
    private BigDecimal chromeFee;
    @TableField(exist = false)
    private String specification;

}
