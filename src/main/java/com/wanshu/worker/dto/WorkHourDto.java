package com.wanshu.worker.dto;


import com.wanshu.worker.entity.Workhours;
import lombok.Data;

@Data
public class WorkHourDto  extends Workhours {

    private String workerName;
}
