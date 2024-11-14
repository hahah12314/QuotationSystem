package com.wanshu.worker.dto;

import com.wanshu.common.model.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class WorkerQueryDto extends PageDTO {
    @Schema(description = "工人名")
    private String workerName;
}
