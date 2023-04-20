package com.gymms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("good")
public class Good {
    @TableId
    private Integer goodId;

    private String createTime;
    private String name;
    private String picture;
    private Double price;
    private String description;
    private Integer store;

}
