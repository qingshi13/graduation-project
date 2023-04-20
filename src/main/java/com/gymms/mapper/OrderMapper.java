package com.gymms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gymms.entity.Cart;
import com.gymms.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
    Page<Orders> page(Page<Orders> page, @Param("id") Integer id, @Param("name") String name, @Param("state") String state);

    Page<Orders> getAallorders(Page<Orders> page,@Param("no") String no,@Param("state") String state);
}
