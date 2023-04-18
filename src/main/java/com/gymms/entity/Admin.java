package com.gymms.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("admin")
public class Admin {
    @TableId(value = "admin_id")
    private Integer AdminId;
    private String createTime;
    private String nickName;
    private String name;
    private String password;
    private String picture;
    private String phoneNumber;
}
