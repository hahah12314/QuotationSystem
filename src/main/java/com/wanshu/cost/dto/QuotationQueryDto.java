package com.wanshu.cost.dto;


import com.wanshu.common.model.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QuotationQueryDto extends PageDTO {
    @Schema(description = "报价名")
    private String quotationName;
}
