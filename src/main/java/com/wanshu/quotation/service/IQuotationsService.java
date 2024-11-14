package com.wanshu.quotation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.quotation.dto.QuotationFormDto;
import com.wanshu.quotation.dto.QuotationQueryDto;
import com.wanshu.quotation.entity.Quotations;
import com.wanshu.quotation.vo.RawMaterialQueryVo;

import java.util.List;

/**
 * <p>
 * 报价单表 服务类
 * </p>
 *
 * @author hehe
 * @since 2024-10-13
 */
public interface IQuotationsService extends IService<Quotations> {

    PageUtils queryPageQuotation(QuotationQueryDto quotationQueryDto);

    RawMaterialQueryVo queryRawMaterial(RawMaterialQueryDto queryDto);

    void saveQuotation(QuotationFormDto quotationForm);

    List<QuotationFormDto> queryAuditPageQuotation();

    void auditQuotation(Long quotationId);
}
