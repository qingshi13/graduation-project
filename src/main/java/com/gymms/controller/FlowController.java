package com.gymms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gymms.entity.Flow;
import com.gymms.entity.Member;
import com.gymms.service.FlowService;
import com.gymms.service.MemberService;
import com.gymms.util.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.text.ParseException;


@RestController
@RequestMapping("/flow")
public class FlowController {
    @Resource
    private FlowService flowService;
    @Resource
    private MemberService memberService;

    @GetMapping("/getflow/{date}")
    public Result getflow(@PathVariable String date){
        return Result.success(flowService.getflow(date));
    }

    @PostMapping("/add")
    public Result getflow(@RequestBody Flow flow){
        Member member = memberService.getById(flow.getMemberId());
        if (member.getState().equals("未开通")){
            return Result.failed("您还未开通会员，请开通会员后预约");
        }
        if (member.getState().equals("已过期")){
            return Result.failed("您的会员已过期，请续费会员后预约");
        }
        QueryWrapper<Flow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date",flow.getDate());
        queryWrapper.eq("time",flow.getTime());
        if (flowService.getOne(queryWrapper) == null){
            flow.setFlowId(0);
            flow.setNum(1);

            member.setTimes(member.getTimes()-1);
            memberService.updateById(member);
            return Result.success(flowService.saveOrUpdate(flow));
        }

        member.setTimes(member.getTimes()-1);
        memberService.updateById(member);
        flow.setFlowId(flowService.getOne(queryWrapper).getFlowId());
        flow.setNum(flowService.getOne(queryWrapper).getNum()+1);
        return Result.success(flowService.saveOrUpdate(flow));
    }

    @GetMapping("/getweek/{date}")
    public Result getweek(@PathVariable String date) throws ParseException {

        return Result.success(flowService.getweek(date));

    }
}
