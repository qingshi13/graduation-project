package com.gymms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("message")
public class Message {
    @TableId(value = "message_id")
    private Integer messageId;
    private String createTime;
    private String title;
    private String content;
}
