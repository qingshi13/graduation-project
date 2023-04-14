package com.gymms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("appointment")
public class Appointment {
    @TableId(value = "appointment_id")
    private Integer appointmentId;

    private Integer memberId;
    private Integer courseId;
    private Integer coachId;
    private String date;
    private String time;
    private String point;
    private String message;

    @TableField(exist = false)
    private String memberName;
    @TableField(exist = false)
    private String courseName;
}
