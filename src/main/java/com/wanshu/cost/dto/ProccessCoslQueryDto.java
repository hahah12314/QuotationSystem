package com.wanshu.cost.dto;


import com.wanshu.common.model.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProccessCoslQueryDto extends PageDTO {
    @Schema(description = "材料规格")
    private String specification;
}
