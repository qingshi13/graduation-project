package com.gymms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gymms.entity.Recharge;
import com.gymms.mapper.RechargeMapper;
import com.gymms.service.RechargeService;
import org.springframework.stereotype.Service;

@Service
public class RechargeServiceImpl extends ServiceImpl<RechargeMapper, Recharge> implements RechargeService {
}
