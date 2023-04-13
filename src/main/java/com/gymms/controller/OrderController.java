package com.gymms.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gymms.entity.Cart;
import com.gymms.entity.Good;
import com.gymms.entity.Orders;
import com.gymms.entity.OrderGood;
import com.gymms.entity.dto.GoodDto;
import com.gymms.mapper.CartMapper;
import com.gymms.mapper.OrderGoodMapper;
import com.gymms.mapper.OrderMapper;
import com.gymms.service.CartService;
import com.gymms.service.GoodService;
import com.gymms.service.OrderGoodService;
import com.gymms.service.OrderService;
import com.gymms.util.Result;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderGoodMapper orderGoodMapper;
    @Resource
    private OrderGoodService orderGoodService;
    @Resource
    private GoodService goodService;
    @Resource
    private CartService cartService;
    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Orders order) {
        if (order.getOrderId() == null) {
            Date date = new Date();
            order.setOrderId(0);
            order.setTime(DateUtil.now());
            order.setNo(DateUtil.format(date,"yyyyMMdd") + System.currentTimeMillis());
            orderService.saveOrUpdate(order);
            List<Cart> carts = order.getCart();
            if (carts == null){
                OrderGood orderGood = new OrderGood();
                orderGood.setGoodId(order.getGoodId());
                orderGood.setNum(order.getNum());
                orderGood.setOrderId(order.getOrderId());
                orderGood.setId(0);
                orderGoodService.save(orderGood);
                return Result.success();
            }
            for (Cart cart : carts) {
                OrderGood orderGood = new OrderGood();
                orderGood.setGoodId(cart.getGoodId());
                orderGood.setNum(cart.getNum());
                orderGood.setOrderId(order.getOrderId());
                orderGood.setId(0);
                orderGoodService.save(orderGood);

                cartService.removeById(cart.getCartId());
            }
        } else {
            orderService.updateById(order);
        }
        return Result.success();
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String state,
                           @RequestParam Integer Id,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        return Result.success(orderMapper.page(new Page<>(pageNum, pageSize), Id, name, state));
    }

    @GetMapping("/getGoodsById/{id}")
    public Result getGoodsById(@PathVariable Integer id) {
        QueryWrapper<OrderGood> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq( "order_id",id);
        ArrayList<GoodDto> goodsList = new ArrayList<>();
        List<OrderGood> orderGoodList = orderGoodService.list(queryWrapper);
        for (OrderGood orderGood : orderGoodList) {
            Integer goodId = orderGood.getGoodId();
            Orders order = orderService.getById(id);
            Good good = goodService.getById(goodId);
            GoodDto goodDto = new GoodDto();
            goodDto.setName(good.getName());
            goodDto.setDescription(good.getDescription());
            goodDto.setPicture(good.getPicture());
            goodDto.setTime(order.getTime());
            goodDto.setPrice(good.getPrice());
            goodDto.setNum(orderGood.getNum());
            goodsList.add(goodDto);
        }
        return Result.success(goodsList);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        orderService.removeById(id);
        QueryWrapper<OrderGood> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq( "order_id",id);
        orderGoodMapper.delete(queryWrapper);
        return Result.success();
    }

    @PostMapping("/pay")
    public Result pay(@RequestBody Orders order) {

        return orderService.pay(order.getOrderId());
    }
}
