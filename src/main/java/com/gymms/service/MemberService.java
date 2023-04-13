package com.gymms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gymms.entity.Member;
import com.gymms.entity.dto.LoginFormDto;
import com.gymms.util.Result;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface MemberService extends IService<Member> {
//    List<Member> getAllMember();
    Result sendCode(String phone, HttpSession session);
    Result login(LoginFormDto loginForm, HttpSession session);

}
