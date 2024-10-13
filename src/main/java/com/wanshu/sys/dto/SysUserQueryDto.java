package com.wanshu.sys.dto;


import com.wanshu.common.model.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysUserQueryDto extends PageDTO {
    @Schema(description = "用户名")
    private String username;
}
