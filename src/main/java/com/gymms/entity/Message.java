package com.gymms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("message")
public class Message {
    private Integer messageId;
    private String createTime;
    private Integer userId;
    private String content;
    private String reply;
}
