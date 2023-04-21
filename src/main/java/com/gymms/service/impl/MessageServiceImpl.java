package com.gymms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gymms.entity.Message;
import com.gymms.mapper.MessageMapper;
import com.gymms.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Resource
    private MessageMapper messageMapper;
    @Override
    public ArrayList<Message> getmessage() {
        return messageMapper.getmessage();
    }
}
