package com.wanshu.cost.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 报价单表
 * </p>
 *
 * @author hehe
 * @since 2024-10-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("quotations")
@ApiModel(value="Quotations对象", description="报价单表")
public class Quotations implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "报价单ID")
    @TableId(value = "quotation_id", type = IdType.AUTO)
    private Long quotationId;

    @ApiModelProperty(value = "报价名称")
    private String quotationName;

    @ApiModelProperty(value = "物料编码")
    private String materialCode;

    @ApiModelProperty(value = "物料名称")
    private String materialName;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "材料规格")
    private String specification;

    @ApiModelProperty(value = "是否外采")
    private Integer isExternalProcurement;

    @ApiModelProperty(value = "产品合计报价")
    private BigDecimal totalCost;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "审核状态")
    private Integer auditStatus;


}
