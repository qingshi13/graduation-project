package com.gymms.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.tokenizer.TokenizerUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gymms.entity.Cart;
import com.gymms.entity.Good;
import com.gymms.mapper.CartMapper;
import com.gymms.service.CartService;
import com.gymms.util.Result;
import org.apache.el.parser.Token;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class CartController {
    @Resource
    private CartService cartService;
    @Resource
    private CartMapper cartMapper;

    @PostMapping("/cart")
    public Result save(@RequestBody Cart cart){

        // 更新购物车里相同的商品的数量
        Integer goodId = cart.getGoodId();
        Integer userId = cart.getUserId() ;
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq( "user_id",userId);
        queryWrapper.eq( "good_id",goodId);
        Cart db = cartService.getOne(queryWrapper);
        if (db != null) {
            db.setNum(db.getNum() + cart.getNum());
            return Result.success(cartService.updateById(db));
        }

        if (cart.getCartId() == null){
            cart.setCartId(0);
            cart.setTime(DateUtil.now());
        }
        return Result.success(cartService.saveOrUpdate(cart));
    }

    @GetMapping("/cart/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer Id,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {

        return Result.success(cartMapper.page(new Page<>(pageNum, pageSize), Id, name));
    }
    //增加购物车内商品数量
    @PostMapping("/cart/num/{id}/{num}")
    public Result updateNum(@PathVariable Integer id,@PathVariable Integer num){
        cartMapper.updateNum(num, id);
        return Result.success();
    }

    @DeleteMapping("/cart/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(cartService.removeById(id));
    }
}
