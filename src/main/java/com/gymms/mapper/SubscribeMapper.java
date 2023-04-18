package com.gymms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gymms.entity.Orders;
import com.gymms.entity.Subscribe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SubscribeMapper extends BaseMapper<Subscribe> {
    Page<Subscribe> page(Page<Subscribe> page, @Param("id") Integer id, @Param("name") String name, @Param("courseNature") String courseNature);
}
