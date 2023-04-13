package com.gymms.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gymms.entity.Member;
import com.gymms.entity.dto.LoginFormDto;
import com.gymms.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.gymms.util.Result;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RestController
//@CrossOrigin
//@RequestMapping("/member")
public class MemberController {
    @Resource
    private MemberService memberService;

//    @GetMapping("/getAllMember")
//    @ResponseBody
//    public List<Member> getAll(){
//        List<Member> member = memberService.getAllMember();
//        return member;
//    }

    /**
     * 发送手机验证码
     */
    @PostMapping("code")
    public Result sendCode(@RequestParam("phone") String phone, HttpSession session) {
        // 发送短信验证码并保存验证码
        return memberService.sendCode(phone, session);
    }

    /**
     * 登录功能
     * @param loginForm 登录参数，包含手机号、验证码；或者手机号、密码
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDto loginForm, HttpSession session){
        // 实现登录功能
        return memberService.login(loginForm, session);
    }

    /**
     * 根据id查会员
     * @param memberId
     */
    @GetMapping("/member/getByMemberId/{memberId}")
    public Result getByMemberId(@PathVariable Integer memberId){

        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id",memberId);
        Member one = memberService.getOne(queryWrapper);
        return Result.success(one);
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
}