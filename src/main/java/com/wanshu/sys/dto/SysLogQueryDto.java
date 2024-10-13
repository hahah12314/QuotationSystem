package com.wanshu.sys.dto;


import com.wanshu.common.model.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysLogQueryDto extends PageDTO {
    @Schema(description = "用户名/用户操作")
    private String msg;
}
