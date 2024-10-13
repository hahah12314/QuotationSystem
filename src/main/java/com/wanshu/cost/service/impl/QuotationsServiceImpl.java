package com.wanshu.cost.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.QuotationQueryDto;
import com.wanshu.cost.entity.Materials;
import com.wanshu.cost.entity.Quotations;
import com.wanshu.cost.mapper.QuotationsMapper;
import com.wanshu.cost.service.IQuotationsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 报价单表 服务实现类
 * </p>
 *
 * @author hehe
 * @since 2024-10-13
 */
@Service
public class QuotationsServiceImpl extends ServiceImpl<QuotationsMapper, Quotations> implements IQuotationsService {

    @Override
    public PageUtils queryPageQuotation(QuotationQueryDto quotationQueryDto) {
        QueryWrapper<Quotations> wrapper=  new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(quotationQueryDto.getQuotationName()), "quotation_name", quotationQueryDto.getQuotationName());
        Page<Quotations> page = this.page(quotationQueryDto.page(),wrapper);
        return new PageUtils(page);
    }
}
