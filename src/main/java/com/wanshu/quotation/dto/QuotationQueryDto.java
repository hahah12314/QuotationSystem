package com.wanshu.quotation.dto;


import com.wanshu.common.model.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QuotationQueryDto extends PageDTO {
    @Schema(description = "客户名")
    private String customerName;
}
