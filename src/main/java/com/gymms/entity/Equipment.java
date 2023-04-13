package com.gymms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("equipment")
public class Equipment {
    private Integer equipmentId;
    private String createTime;
    private String type;
    private String name;
    private String picture;
    private String brief;
    private Integer quantity;
    private Double price;
}
