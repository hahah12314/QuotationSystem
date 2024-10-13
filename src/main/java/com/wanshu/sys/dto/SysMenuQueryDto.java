package com.wanshu.sys.dto;


import com.wanshu.common.model.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysMenuQueryDto extends PageDTO {
    @Schema(description = "菜单信息")
    private String name;
}
