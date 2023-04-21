package com.gymms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gymms.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    ArrayList<Message> getmessage();
}
