package com.wanshu.quotation.controller;


import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.quotation.dto.QuotationFormDto;
import com.wanshu.quotation.dto.QuotationQueryDto;
import com.wanshu.quotation.service.IQuotationsService;
import com.wanshu.quotation.vo.RawMaterialQueryVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public PageUtils queryPage(@ApiParam(value = "客户名") QuotationQueryDto quotationQueryDto){
        log.info("queryPage{}",quotationQueryDto);

        return this.quotationsService.queryPageQuotation(quotationQueryDto);
    }
    @GetMapping("/audit")
    @ApiOperation(value = "查询审核报价单")
    public List<QuotationFormDto> queryAuditPage(){
        log.info("queryAuditPage{}");

        return this.quotationsService.queryAuditPageQuotation();
    }
    @GetMapping("/auditQuotation")
    @ApiOperation(value = "查询指定ID的审核报价单")
    public void AuditQuotation(@RequestParam Long quotationId) {
        log.info("AuditQuotation{}",quotationId);
        this.quotationsService.auditQuotation(quotationId);
    }
    @GetMapping("/getDetail")
    @ApiOperation(value = "查询单个产品明细")
    public RawMaterialQueryVo queryRawMaterial(RawMaterialQueryDto queryDto){
        log.info("queryRawMaterial{}",queryDto);
        return quotationsService.queryRawMaterial(queryDto);

    }
    @PostMapping("/save")
    @ApiOperation(value = "保存报价单")
    public void saveQuotation(@RequestBody QuotationFormDto quotationForm) {
        log.info("saveQuotation{}", quotationForm);
        this.quotationsService.saveQuotation(quotationForm);
    }


}
