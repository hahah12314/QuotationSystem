package com.wanshu.cost.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

     @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
