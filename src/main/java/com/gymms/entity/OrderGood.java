package com.gymms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("order_good")
public class OrderGood {
    @TableId
    private Integer id;

    private Integer goodId;
    private Integer orderId;
    private Integer num;
}
