package com.gymms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gymms.entity.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CartMapper extends BaseMapper<Cart> {
    Page<Cart> page(Page<Cart> page, @Param("id") Integer id, @Param("name") String name);

    @Update("update cart set num = #{num} where cart_id = #{id}")
    void updateNum(@Param("num") Integer num,@Param("id") Integer id);
}
