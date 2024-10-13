package com.wanshu.sys.dto;


import com.wanshu.common.model.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysRoleQueryDto extends PageDTO {
    @Schema(description = "角色名")
    private String roleName;
}
