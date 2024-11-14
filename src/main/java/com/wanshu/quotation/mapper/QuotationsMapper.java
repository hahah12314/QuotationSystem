package com.wanshu.quotation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanshu.quotation.entity.Quotations;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 报价单表 Mapper 接口
 * </p>
 *
 * @author hehe
 * @since 2024-10-13
 */
@Mapper
public interface QuotationsMapper extends BaseMapper<Quotations> {

}
