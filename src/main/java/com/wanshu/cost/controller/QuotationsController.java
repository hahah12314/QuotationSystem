package com.wanshu.cost.controller;


import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.QuotationQueryDto;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.cost.service.IQuotationsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 报价单表 前端控制器
 * </p>
 *
 * @author hehe
 * @since 2024-10-13
 */
@RestController
@RequestMapping("/quotations")
@Slf4j
public class QuotationsController {
    @Autowired
    private IQuotationsService quotationsService;
    @GetMapping("/list")
    @ApiOperation(value = "分页查询报价单")
    public PageUtils queryPage(@ApiParam(value = "报价名")QuotationQueryDto quotationQueryDto){
        log.info("queryPage{}",quotationQueryDto);

        return this.quotationsService.queryPageQuotation(quotationQueryDto);
    }

}
