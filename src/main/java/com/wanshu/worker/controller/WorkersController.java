package com.wanshu.worker.controller;


import com.wanshu.common.util.PageUtils;
import com.wanshu.worker.dto.WorkDto;
import com.wanshu.worker.dto.WorkerQueryDto;
import com.wanshu.worker.entity.Workers;
import com.wanshu.worker.service.IWorkersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工人表 前端控制器
 * </p>
 *
 * @author hehe
 * @since 2024-11-03
 */
@RestController
@RequestMapping("/workers")
@Slf4j
@Api(tags = "工人管理")
public class WorkersController {
    @Autowired
    private IWorkersService workersService;
    @GetMapping("/list")
    @ApiOperation(value = "分页查询工人")
    public PageUtils queryPage(@ApiParam(value = "工人名")WorkerQueryDto workerQueryDto){
        log.info("queryPage{}",workerQueryDto);

        return this.workersService.queryPageWorker(workerQueryDto);
    }
    @GetMapping("/getWorkerAll")
    @ApiOperation(value = "查询工人")
    public List<Workers> queryAllWorker(){
        log.info("queryAllWorker");
        return  this.workersService.list();
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "查询工人")
    public Map<String, List<WorkDto>> queryAll(){
        log.info("queryAll");

        return this.workersService.queryAll();
    }

    //lzy写的增删改后端皆实现
    @PostMapping("/save")
    @ApiOperation(value= "新增工人")
    public boolean save(@ApiParam(value = "工人信息") @RequestBody Workers workers){
        log.info("save:{}",workers);
        return this.workersService.saveworkers(workers);
    }
    /*
    {
    "id" : 23,
    "processName": "钻孔",
    "workerId": 6,
    "cost": 45.00,
    "hour": 1
}

     */



    @PostMapping("/update")
    @ApiOperation(value = "更新用户")
    public boolean update(@ApiParam(value = "用户信息") @RequestBody Workers workers){
        log.info("update:{}",workers);
        return this.workersService.updateWorkers(workers);

    }

    //postman传json字符串删除
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除工人")
    public String deleteWorkers(@RequestBody Map<String, Object> request) {
        int id = (int) request.get("id");
        log.info("deleteWorkers:{}", id);
        return this.workersService.deleteWorkers(id);
    }


    //postman传url形式
//    @DeleteMapping("/delete")
//    @ApiOperation(value = "删除工人")
//    public String deleteWorkers(@ApiParam(value = "id") @RequestParam("id") Integer id) {
//        //System.out.println("Controller收到的id: " + id);
//        return this.workersService.deleteWorkers(id);
//    }


}
