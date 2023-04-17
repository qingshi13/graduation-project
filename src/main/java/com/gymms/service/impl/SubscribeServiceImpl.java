package com.gymms.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gymms.entity.*;
import com.gymms.mapper.SubscribeMapper;
import com.gymms.service.CoachService;
import com.gymms.service.CourseService;
import com.gymms.service.MemberService;
import com.gymms.service.SubscribeService;
import com.gymms.util.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SubscribeServiceImpl  extends ServiceImpl<SubscribeMapper, Subscribe> implements SubscribeService {
    @Resource
    private MemberService memberService;
    @Resource
    private CourseService courseService;
    @Resource
    private CoachService coachService;

    @Override
    public Result subscribe(Subscribe subscribe) {
        Subscribe sub = query().eq("course_id",subscribe.getCourseId())
                .eq("member_id",subscribe.getMemberId()).one();
        if (sub != null){
            return Result.failed("您已订阅该课程");
        }
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<Member>().eq("member_id", subscribe.getMemberId());
        Member member = memberService.getOne(memberQueryWrapper);
        if (member.getState().equals("未开通")){
            return Result.failed("您未开通会员，请开通后再订阅该课程");
        } else if (member.getState().equals("已过期")){
            return Result.failed("您的会员已过期，请续费后再订阅该课程");
        }
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<Course>().eq("course_id", subscribe.getCourseId());
        Course course = courseService.getOne(courseQueryWrapper);
        if (member.getRemainingSum() < course.getCourseFees()){
            return Result.failed("余额不足，请充值");
        }
        member.setAccumulatePoints(member.getAccumulatePoints() + (int) Math.ceil(course.getCourseFees()));
        member.setRemainingSum(member.getRemainingSum() - course.getCourseFees());
        memberService.updateById(member);
        subscribe.setSubscribeId(0);
        subscribe.setSubscriptionFee(course.getCourseFees());
        subscribe.setTime(DateUtil.now());
        subscribe.setCourseNumber(course.getNum());
        this.saveOrUpdate(subscribe);
        Coach coach = coachService.getById(course.getCoachId());
        if (coach.getRecruitNumber() == null){
            coach.setRecruitNumber(1);
        }
        coach.setRecruitNumber(coach.getRecruitNumber()+1);
        coachService.updateById(coach);
        return Result.success("订阅成功");
    }
}
