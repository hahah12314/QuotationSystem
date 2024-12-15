package com.wanshu.message.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanshu.message.entity.Messages;
import com.wanshu.message.mapper.MessagesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessagesController {

    @Autowired
    private MessagesMapper messagesMapper;

    // 查询全部未读消息
    @GetMapping("/getAll")
    public List<Messages> getAllMessages(@RequestParam Long receiverId) {
        System.out.println("receiverId: " + receiverId);
        QueryWrapper<Messages> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver_id", receiverId);
        queryWrapper.eq("is_read", 0);
        queryWrapper.orderByDesc("created_at");
        return messagesMapper.selectList(queryWrapper);
    }
    // 查询未读消息
    @GetMapping("/unread")
    public List<Messages> getUnreadMessages(@RequestParam Long receiverId, @RequestParam Long senderId) {
        System.out.println("receiverId: " + receiverId+"  senderId: " + senderId);
        QueryWrapper<Messages> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sender_id", senderId);
        queryWrapper.eq("receiver_id", receiverId).eq("is_read", 0);
        return messagesMapper.selectList(queryWrapper);
    }

    // 查询已读消息（最多5条）
    @GetMapping("/read")
    public List<Messages> getReadMessages(@RequestParam Long receiverId, @RequestParam Long senderId) {
        QueryWrapper<Messages> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sender_id", senderId);
        queryWrapper.eq("receiver_id", receiverId).eq("is_read", 1);
        Page<Messages> page = new Page<>(1, 5);
        return messagesMapper.selectPage(page, queryWrapper).getRecords();
    }

    // 发送消息
    @PostMapping("/send")
    public void sendMessage(@RequestBody Messages message) {
        messagesMapper.insert(message);
    }

    // 更新消息状态为已读
    @GetMapping("/markRead")
    public void markMessageAsRead(@RequestParam Long receiverId, @RequestParam Long senderId) {
        QueryWrapper<Messages> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sender_id", senderId);
        queryWrapper.eq("receiver_id", receiverId);
        List<Messages> messagesList = messagesMapper.selectList(queryWrapper);
        for (Messages message : messagesList) {
            message.setIsRead(true);
            message.setUpdatedAt(LocalDateTime.now());
            messagesMapper.updateById(message);
        }
    }

}
