package com.wanshu.worker.dto;

import com.wanshu.worker.entity.Workers;
import com.wanshu.worker.entity.Workhours;
import lombok.Data;

@Data
public class WorkDto extends Workhours {
    private String workerName;
    private String level;


}
