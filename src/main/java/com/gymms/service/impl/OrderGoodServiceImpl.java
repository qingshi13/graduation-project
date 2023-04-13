package com.gymms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gymms.entity.OrderGood;
import com.gymms.mapper.OrderGoodMapper;
import com.gymms.service.OrderGoodService;
import org.springframework.stereotype.Service;

@Service
public class OrderGoodServiceImpl extends ServiceImpl<OrderGoodMapper, OrderGood> implements OrderGoodService {
}
