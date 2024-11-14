package com.wanshu.worker.dto;

import com.wanshu.common.model.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class WorkerHourQueryDto extends PageDTO {
    @Schema(description = "加工名")
    private String processName;
}
