package com.gymms.controller;

import cn.hutool.core.date.DateUtil;
import com.gymms.entity.Message;
import com.gymms.service.MessageService;
import com.gymms.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageService messageService;
    @PostMapping("/release")
    public Result release(@RequestBody Message message){
        message.setCreateTime(DateUtil.now());
        message.setMessageId(0);
        return Result.success(messageService.saveOrUpdate(message));
    }
}
