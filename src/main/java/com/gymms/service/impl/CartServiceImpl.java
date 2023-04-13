package com.gymms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gymms.entity.Cart;
import com.gymms.entity.Good;
import com.gymms.mapper.CartMapper;
import com.gymms.mapper.GoodMapper;
import com.gymms.service.CartService;
import com.gymms.service.GoodService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
}