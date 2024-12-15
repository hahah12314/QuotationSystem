package com.wanshu.message.service.impl;

import com.wanshu.message.entity.Messages;
import com.wanshu.message.mapper.MessagesMapper;
import com.wanshu.message.service.IMessagesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息表，用于存储通知消息，支持针对不同角色的消息推送 服务实现类
 * </p>
 *
 * @author hehe
 * @since 2024-12-13
 */
@Service
public class MessagesServiceImpl extends ServiceImpl<MessagesMapper, Messages> implements IMessagesService {

}
