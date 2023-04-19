package com.gymms.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gymms.entity.Member;
import com.gymms.entity.Recharge;
import com.gymms.entity.dto.LoginFormDto;
import com.gymms.entity.dto.RenewDto;
import com.gymms.service.MemberService;
import com.gymms.service.RechargeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.gymms.util.Result;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Controller
@RestController
//@CrossOrigin
public class MemberController {
    @Resource
    private MemberService memberService;
    @Resource
    private RechargeService rechargeService;

//    @GetMapping("/getAllMember")
//    @ResponseBody
//    public List<Member> getAll(){
//        List<Member> member = memberService.getAllMember();
//        return member;
//    }

    /**
     * 发送手机验证码
     */
    @PostMapping("/code")
    public Result sendCode(@RequestParam("phone") String phone) {
        // 发送短信验证码并保存验证码
        return memberService.sendCode(phone);
    }

    /**
     * 登录功能
     * @param loginForm 登录参数，包含手机号、验证码；或者手机号、密码
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDto loginForm){
        // 实现登录功能
        return memberService.login(loginForm);
    }
    /**
     * 注册功能
     * @param loginForm 登录参数，包含手机号、验证码；或者手机号、密码
     */
    @PostMapping("/register")
    public Result register(@RequestBody LoginFormDto loginForm){
        // 实现登录功能
        return memberService.register(loginForm);
    }

    /**
     * 根据id查会员
     * @param memberId
     */
    @GetMapping("/member/getByMemberId/{memberId}")
    public Result getByMemberId(@PathVariable Integer memberId){

        return Result.success(memberService.getById(memberId));
    }

    /**
     * 新增或更新
     * @param
     */
    @PostMapping("/member/save")
    public Result save(@RequestBody Member member){
        Integer memberId = member.getMemberId();
        if (StrUtil.isBlank((memberId).toString())){
            return Result.validateFailed( "参数错误");
        }
        return Result.success(memberService.saveOrUpdate(member));
    }

    @PostMapping("/member/recharge")
    public Result recharge(@RequestBody Recharge recharge){
        Integer memberId = recharge.getUserId();
        if (StrUtil.isBlank((memberId).toString())){
            return Result.validateFailed( "参数错误");
        }
        if (recharge.getAmount() == null){
            return Result.failed("请输入充值金额");
        }
        Member member = memberService.getById(memberId);
        member.setRemainingSum(recharge.getAmount()+member.getRemainingSum());
        recharge.setRechargeId(0);
        recharge.setTime(DateUtil.now());
        rechargeService.saveOrUpdate(recharge);
        return Result.success(memberService.saveOrUpdate(member),"充值成功");
    }
    @PostMapping("/member/renew")
    public Result renew(@RequestBody RenewDto renewDto) throws Exception{
        Member member = memberService.getById(renewDto.getMemberId());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (renewDto.getRenew() == 1){
            System.out.println(DateUtil.nextMonth().toString());
            if(member.getExpirationTime() == null){
                member.setExpirationTime(DateUtil.nextMonth().toString());
            }

            member.setExpirationTime(DateUtil.offsetMonth(dateFormat.parse(member.getExpirationTime()),1).toString());
            member.setRemainingSum(member.getRemainingSum() - 208);
            member.setAccumulatePoints(member.getAccumulatePoints() + 208);
            member.setState("已生效");
            return Result.success(memberService.updateById(member),"续费成功");
        }
        if (renewDto.getRenew() == 3){
            System.out.println(DateUtil.offsetMonth(DateUtil.nextMonth(),2));
            if(member.getExpirationTime() == null){
                member.setExpirationTime(DateUtil.offsetMonth(DateUtil.nextMonth(),2).toString());
            }
            member.setExpirationTime(DateUtil.offsetMonth(dateFormat.parse(member.getExpirationTime()),3).toString());
            member.setRemainingSum(member.getRemainingSum() - 536);
            member.setAccumulatePoints(member.getAccumulatePoints() + 536);
            member.setState("已生效");
            return Result.success(memberService.updateById(member),"续费成功");

        }
        if (renewDto.getRenew() == 6){
            System.out.println(DateUtil.offsetMonth(DateUtil.nextMonth(),5));
            if(member.getExpirationTime() == null){
                member.setExpirationTime(DateUtil.offsetMonth(DateUtil.nextMonth(),5).toString());
            }
            member.setExpirationTime(DateUtil.offsetMonth(dateFormat.parse(member.getExpirationTime()),6).toString());
            member.setRemainingSum(member.getRemainingSum() - 888);
            member.setAccumulatePoints(member.getAccumulatePoints() + 888);
            member.setState("已生效");
            return Result.success(memberService.updateById(member),"续费成功");

        }
        if (renewDto.getRenew() == 12){
            System.out.println(DateUtil.offsetMonth(DateUtil.nextMonth(),11));
            if(member.getExpirationTime() == null){
                member.setExpirationTime(DateUtil.offsetMonth(DateUtil.nextMonth(),11).toString());
            }
            member.setExpirationTime(DateUtil.offsetMonth(dateFormat.parse(member.getExpirationTime()),12).toString());
            member.setRemainingSum(member.getRemainingSum() - 1499);
            member.setAccumulatePoints(member.getAccumulatePoints() + 1499);
            member.setState("已生效");
            return Result.success(memberService.updateById(member),"续费成功");

        }


        return Result.failed();
    }
}