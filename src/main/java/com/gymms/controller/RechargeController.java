package com.gymms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gymms.entity.Good;
import com.gymms.entity.Recharge;
import com.gymms.service.RechargeService;
import com.gymms.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/recharge")
public class RechargeController {
    @Resource
    private RechargeService rechargeService;
    @GetMapping("/member")
    public Result member(@RequestParam Integer memberId,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Recharge> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("recharge_id");
        queryWrapper.eq("user_id",memberId);
        return Result.success(rechargeService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}
