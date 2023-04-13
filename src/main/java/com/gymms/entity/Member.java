package com.gymms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("member")
public class Member {
    @TableId
    private Integer memberId;

    private String createTime;
    private String nickName;
    private String name;
    private String password;
    private String picture;
    private String sex;
    private String phoneNumber;
    private Double remainingSum;
    private Integer accumulatePoints;
    private Integer courseId;
    private String address;
    private String expirationTime;
    private String state;
}
