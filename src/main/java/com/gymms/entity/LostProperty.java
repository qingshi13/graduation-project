package com.gymms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("lostproperty")
public class LostProperty {
    private Integer lostPropertyId;
    private String createTime;
    private String picture;
    private String information;
    private Integer status;
}
