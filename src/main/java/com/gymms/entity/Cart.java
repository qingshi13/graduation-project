package com.gymms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("cart")
public class Cart {
    @TableId
    private Integer cartId;

    private Integer goodId;
    private Integer userId;
    private String Time;
    private Integer num;

    @TableField(exist = false)
    private String goodName;
    @TableField(exist = false)
    private String goodPicture;
    @TableField(exist = false)
    private BigDecimal price;
    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String nickname;

}
