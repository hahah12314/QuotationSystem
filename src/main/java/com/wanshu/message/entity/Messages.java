package com.wanshu.message.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 消息表，用于存储通知消息，支持针对不同角色的消息推送
 * </p>
 *
 * @author hehe
 * @since 2024-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("messages")
public class Messages implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
    @TableId(value = "message_id", type = IdType.AUTO)
    private Long messageId;

    /**
     * 发送人角色ID
     */
    private Long senderId;

    /**
     * 接收人角色ID
     */
    private Long receiverId;

    /**
     * 关联报价单ID
     */
    private Long relatedQuoteId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 是否已读，0未读，1已读
     */
    private Boolean isRead;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


}
