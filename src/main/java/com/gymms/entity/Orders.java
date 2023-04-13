package com.gymms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("orders")
public class Orders {
    @TableId(value = "order_id",type = IdType.AUTO)
    private Integer orderId;

    private String name;
    private String no;
    private String time;
    private String payTime;
    private String state;
    private Double totalPrice;
    private Integer userId;

    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String nickName;
    @TableField(exist = false)
    private List<Cart> cart;
    @TableField(exist = false)
    private Integer goodId;
    @TableField(exist = false)
    private Integer num;
}
