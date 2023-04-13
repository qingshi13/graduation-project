package com.gymms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gymms.entity.Coach;
import com.gymms.entity.dto.LimitDto;
import com.gymms.entity.dto.LoginFormDto;
import com.gymms.util.Result;

import javax.servlet.http.HttpSession;

public interface CoachService extends IService<Coach> {
    LimitDto limit(Integer coachId);
//    Result login(LoginFormDto loginForm, HttpSession session);

}
