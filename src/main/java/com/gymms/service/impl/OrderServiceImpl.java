package com.gymms.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gymms.entity.*;
import com.gymms.entity.dto.GoodDto;
import com.gymms.mapper.OrderMapper;
import com.gymms.service.*;
import com.gymms.util.Result;
import javafx.scene.control.skin.CellSkinBase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
    @Resource
    private OrderGoodService orderGoodService;
    @Resource
    private CoachService coachService;
    @Resource
    private MemberService memberService;
    @Resource
    private GoodService goodService;

    @Override
    public Result pay(Integer orderId) {
        Orders order = query().eq("order_id",orderId).one();
        Boolean store = true;
        int userId = order.getUserId();

        if (order.getState().equals("已支付")){
            return Result.failed("订单已支付");
        }

        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<Member>().eq("member_id", userId);
        Member member = memberService.getOne(memberQueryWrapper);

        QueryWrapper<Coach> coachQueryWrapper = new QueryWrapper<Coach>().eq("coach_id", userId);
        Coach coach = coachService.getOne(coachQueryWrapper);

        QueryWrapper<OrderGood> orderGoodWrapper = new QueryWrapper<>();
        orderGoodWrapper.eq( "order_id",orderId);
        List<OrderGood> orderGoodList = orderGoodService.list(orderGoodWrapper);
        for (OrderGood orderGood : orderGoodList) {
            Integer goodId = orderGood.getGoodId();
            Good good = goodService.getById(goodId);
            if (good.getStore() < orderGood.getNum()) {
                store = false;
                break;
            }
        }
        if (store){
            if (member != null){
                if (member.getRemainingSum() >= order.getTotalPrice()) {
                    for (OrderGood orderGood : orderGoodList) {
                        Integer goodId = orderGood.getGoodId();
                        Good good = goodService.getById(goodId);
                        good.setStore(good.getStore()-orderGood.getNum());
                        goodService.updateById(good);
                    }

                    member.setAccumulatePoints(member.getAccumulatePoints() + (int) Math.ceil(order.getTotalPrice()));
                    member.setRemainingSum(member.getRemainingSum() - order.getTotalPrice());
                    memberService.updateById(member);
                }else {
                    return Result.failed("余额不足，请充值");
                }
            }
            if (coach != null){
                if (coach.getRemainingSum() >= order.getTotalPrice()) {

                    for (OrderGood orderGood : orderGoodList) {
                        Integer goodId = orderGood.getGoodId();
                        Good good = goodService.getById(goodId);
                        good.setStore(good.getStore()-orderGood.getNum());
                        goodService.updateById(good);
                    }

                    coach.setAccumulatePoints(coach.getAccumulatePoints() + (int) Math.ceil(order.getTotalPrice()));
                    coach.setRemainingSum(coach.getRemainingSum() - order.getTotalPrice());
                    coachService.updateById(coach);
                }else {
                    return Result.failed("余额不足，请充值");
                }
            }
        }else{
            return Result.failed("库存不足");
        }
        order.setState("已支付");
        order.setPayTime(DateUtil.now());
        this.updateById(order);
        return Result.success("支付成功");

    }
}
