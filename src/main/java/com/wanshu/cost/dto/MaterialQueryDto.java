package com.wanshu.cost.dto;


import com.wanshu.common.model.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MaterialQueryDto extends PageDTO {
    @Schema(description = "物料名")
    private String materialName;
}
