package com.wanshu.message.service;

import com.wanshu.message.entity.Messages;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 消息表，用于存储通知消息，支持针对不同角色的消息推送 服务类
 * </p>
 *
 * @author hehe
 * @since 2024-12-13
 */
public interface IMessagesService extends IService<Messages> {

}
