package com.gymms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("grade")
public class Grade {
    private String name;
    private Integer min;
    private Integer max;
    private Double discount;
}
