package com.gymms.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gymms.entity.Message;
import com.gymms.service.MessageService;
import com.gymms.util.Result;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getmessage")
    public Result getmessage(){
        return Result.success(messageService.getmessage());
    }

    @GetMapping("/getallmessage")
    public Result getallmessage(@RequestParam(defaultValue = "") String name,
                                @RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("message_id");

        if (!"".equals(name)) {
            queryWrapper.like("title", name);
        }
        return Result.success(messageService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}
