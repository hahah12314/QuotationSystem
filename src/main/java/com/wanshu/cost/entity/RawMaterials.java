package com.wanshu.cost.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 原材料表
 * </p>
 *
 * @author hehe
 * @since 2024-10-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("raw_materials")
@ApiModel(value="RawMaterials对象", description="原材料表")
public class RawMaterials implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一标识符")
    @TableId(value = "detail_id", type = IdType.AUTO)
    private Integer detailId;
    @ApiModelProperty(value = "材料规格")
    private String specification;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "裸价（元）")
    private BigDecimal nakedPrice;

    @ApiModelProperty(value = "利润")
    private BigDecimal profit;

    @ApiModelProperty(value = "包装、运输、财务、管理费用（元）")
    private BigDecimal miscellaneousFees;

    @ApiModelProperty(value = "产品合计报价")
    private BigDecimal totalProductQuote;


}
