package com.wanshu.cost.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 原材料表
 * </p>
 *
 * @author hehe
 * @since 2024-11-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("raw_materials")
public class RawMaterials implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识符
     */
    @TableId(value = "detail_id", type = IdType.AUTO)
    private Integer detailId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 裸价（元）
     */
    private BigDecimal nakedPrice;

    /**
     * 利润
     */
    private BigDecimal profit;

    /**
     * 包装、运输、财务、管理费用（元）
     */
    private BigDecimal miscellaneousFees;

    /**
     * 产品合计报价
     */
    private BigDecimal totalProductQuote;

    /**
     * 材料规格
     */
    private String specification;

    /**
     * 单件重量
     */
    private BigDecimal unitWeight;

    /**
     * 净重
     */
    private BigDecimal netWeight;

    /**
     * 未税
     */
    private BigDecimal preTax;

    /**
     * 公斤价
     */
    private BigDecimal pricePerkg;


}
