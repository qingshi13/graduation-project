package com.gymms.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("coach")
public class Coach {
    @TableId
    private Integer coachId;

    private String createTime;
    private String nickName;
    private String name;
    private String password;
    private String picture;
    private String sex;
    private String phoneNumber;
    private Double remainingSum;
    private Integer accumulatePoints;
    private String position;
    private String description;
    private String address;
    private Integer maxpeople;
    private Integer recruitNumber;
}
