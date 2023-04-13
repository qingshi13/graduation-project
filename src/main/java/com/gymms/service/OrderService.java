package com.gymms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gymms.entity.Orders;
import com.gymms.util.Result;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService extends IService<Orders> {

    @Transactional
    Result pay(Integer orderId);
}
