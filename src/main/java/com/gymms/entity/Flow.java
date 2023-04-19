package com.gymms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("flow")
public class Flow {
    @TableId(value = "flow_id")
    private Integer flowId;
    private String date;
    private String time;
    private Integer num;

    @TableField(exist = false)
    private Integer memberId;
}
