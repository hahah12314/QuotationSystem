package com.wanshu.message.mapper;

import com.wanshu.message.entity.Messages;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 消息表，用于存储通知消息，支持针对不同角色的消息推送 Mapper 接口
 * </p>
 *
 * @author hehe
 * @since 2024-12-13
 */
public interface MessagesMapper extends BaseMapper<Messages> {

}
