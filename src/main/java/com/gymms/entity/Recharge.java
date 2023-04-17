package com.gymms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("recharge")
public class Recharge {
    @TableId(value = "recharge_id",type = IdType.AUTO)
    private Integer rechargeId;

    private Integer userId;
    private String time;
    private Double amount;
}
