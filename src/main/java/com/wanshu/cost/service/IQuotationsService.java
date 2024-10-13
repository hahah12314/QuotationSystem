package com.wanshu.cost.service;

import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.QuotationQueryDto;
import com.wanshu.cost.entity.Quotations;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
