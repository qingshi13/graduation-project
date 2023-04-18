package com.gymms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("subscribe")
public class Subscribe {
    @TableId(value = "subscribe_id",type = IdType.AUTO)
    private Integer subscribeId;
    private Integer memberId;
    private Integer courseId;
    private String time;
    private Double subscriptionFee;
    private Double rate;
    private Integer courseNumber;

    @TableField(exist = false)
    private String coachName;
    @TableField(exist = false)
    private String coachPhone;
    @TableField(exist = false)
    private String courseName;
    @TableField(exist = false)
    private String courseNature;
    @TableField(exist = false)
    private String isapp;
    @TableField(exist = false)
    private String date;
    @TableField(exist = false)
    private String point;
}
