package com.wanshu.cost.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 物料表
 * </p>
 *
 * @author hehe
 * @since 2024-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("materials")
@ApiModel(value="Materials对象", description="物料表")
public class Materials implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "物料ID")
    @TableId(value = "material_id", type = IdType.AUTO)
    private Integer materialId;

    @ApiModelProperty(value = "物料编码")
    private String materialCode;
    @ApiModelProperty(value = "是否外采")
    private Boolean isExternalProcurement;
    @ApiModelProperty(value = "物料名称")
    private String materialName;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "单件重量")
    private Double weight;

    private Integer quantity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


}
