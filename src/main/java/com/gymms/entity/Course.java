package com.gymms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("course")
public class Course {
    @TableId
    private Integer courseId;

    private String createTime;
    private String name;
    private Integer coachId;
    private String description;
    private String picture;
    private Double courseFees;
    private Integer num;
    private String openTime;
    private String courseNature;
    private String state;
    private Double rate;
    private Integer recruit;

    @TableField(exist = false)
    private String coachName;
    @TableField(exist = false)
    private String coachPicture;
}
