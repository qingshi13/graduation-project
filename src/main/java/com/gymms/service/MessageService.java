package com.gymms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gymms.entity.Message;

import java.util.ArrayList;

public interface MessageService extends IService<Message> {
    ArrayList<Message> getmessage();
}
