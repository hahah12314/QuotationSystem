package com.wanshu.quotation.service.impl;

import com.wanshu.quotation.entity.Customer;
import com.wanshu.quotation.mapper.CustomerMapper;
import com.wanshu.quotation.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author hehe
 * @since 2024-11-14
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

}
