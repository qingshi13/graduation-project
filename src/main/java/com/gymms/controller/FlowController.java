package com.gymms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gymms.entity.Flow;
import com.gymms.entity.Member;
import com.gymms.service.FlowService;
import com.gymms.service.MemberService;
import com.gymms.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
        QueryWrapper<Flow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date",flow.getDate());
        queryWrapper.eq("time",flow.getTime());
        if (flowService.getOne(queryWrapper) == null){
            flow.setFlowId(0);
            flow.setNum(1);
            Member member = memberService.getById(flow.getMemberId());
            member.setTimes(member.getTimes()-1);
            memberService.updateById(member);
            return Result.success(flowService.saveOrUpdate(flow));
        }
        Member member = memberService.getById(flow.getMemberId());
        member.setTimes(member.getTimes()-1);
        memberService.updateById(member);
        flow.setFlowId(flowService.getOne(queryWrapper).getFlowId());
        flow.setNum(flowService.getOne(queryWrapper).getNum()+1);
        return Result.success(flowService.saveOrUpdate(flow));
    }
}
