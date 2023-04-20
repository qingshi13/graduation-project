package com.gymms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gymms.entity.Admin;
import com.gymms.entity.Cart;
import com.gymms.entity.Coach;
import com.gymms.entity.Member;
import com.gymms.entity.dto.LoginFormDto;
import com.gymms.entity.dto.UserDto;
import com.gymms.mapper.AdminMapper;
import com.gymms.mapper.MemberMapper;
import com.gymms.service.AdminService;
import com.gymms.service.CoachService;
import com.gymms.service.MemberService;
import com.gymms.util.RegexUtils;
import com.gymms.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import static com.gymms.util.RedisConstants.*;

@Slf4j
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Resource
    private MemberMapper memberMapper;

    @Resource
    private CoachService coachService;
    @Resource
    private AdminService adminService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result sendCode(String phone) {

        System.out.println(phone);
        if (RegexUtils.isPhoneInvalid(phone)) {
            return Result.failed("手机号格式错误！");
        }

        String code = RandomUtil.randomNumbers(4);

        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + phone, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);
        System.out.println(code);
        return Result.success(code,"验证码："+code);
    }

    @Override
    public Result login(LoginFormDto loginForm) {

        if(loginForm.getPhone().isEmpty()||( loginForm.getCode().isEmpty() && loginForm.getPassword().isEmpty() )){

            return Result.failed("填写不能为空");
        }
        String phone = loginForm.getPhone();
        if (RegexUtils.isPhoneInvalid(phone)) {

            return Result.failed("手机号格式错误！");
        }

        UserDto userDto = new UserDto();
        String token = UUID.randomUUID().toString(true);

        Member member = query().eq("phone_number", phone).one();

        QueryWrapper<Coach> coachQueryWrapper = new QueryWrapper<Coach>().eq("phone_number", phone);
        Coach coach = coachService.getOne(coachQueryWrapper);
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<Admin>().eq("phone_number", phone);
        Admin admin = adminService.getOne(adminQueryWrapper);

        if (member == null && coach == null && admin == null) {
            return Result.failed("用户不存在，请注册");
        }

        if (!loginForm.getPassword().isEmpty()){

            QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
            memberQueryWrapper.eq("phone_number", phone);
            memberQueryWrapper.eq("password",loginForm.getPassword());

            Member member1 = this.getOne(memberQueryWrapper);
            coachQueryWrapper.eq("password",loginForm.getPassword());
            Coach coach1 = coachService.getOne(coachQueryWrapper);
            adminQueryWrapper.eq("password",loginForm.getPassword());
            Admin admin1 = adminService.getOne(adminQueryWrapper);
            if (admin1 == null && coach1 == null && member1 == null){
                return Result.failed("密码错误");
            }
            coach = coach1;
            member = member1;
            admin = admin1;
        }else {
            String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);
            String code = loginForm.getCode();
            if (cacheCode == null || !cacheCode.equals(code)) {
                return Result.failed("验证码错误");
            }
        }

        if (coach == null && admin == null){

            userDto.setRole("member");
            userDto.setUserId(member.getMemberId());
            userDto.setNickName(member.getNickName());
            if (member.getPicture() == null)
                userDto.setPicture("null");
            else
                userDto.setPicture(member.getPicture());
        } else if(admin == null){
            userDto.setRole("coach");
            userDto.setUserId(coach.getCoachId());
            userDto.setNickName(coach.getNickName());
            if (coach.getPicture() == null)
                userDto.setPicture("null");
            else
                userDto.setPicture(coach.getPicture());
        }else {
            userDto.setRole("admin");
            userDto.setUserId(admin.getAdminId());
            userDto.setNickName(admin.getNickName());
            if (admin.getPicture() == null)
                userDto.setPicture("null");
            else
                userDto.setPicture(admin.getPicture());

        }

//        UserDto userDto = BeanUtil.copyProperties(member, UserDto.class);
        System.out.println(userDto);
        Map<String, Object> userMap = BeanUtil.beanToMap(userDto, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
        String tokenKey = LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);
        return Result.success(userDto);

    }

    @Override
    public Result register(LoginFormDto loginForm) {

        if(loginForm.getPhone().isEmpty()||loginForm.getPassword().isEmpty()||loginForm.getCode().isEmpty()||loginForm.getNickName().isEmpty()){

            return Result.failed("填写不能为空");
        }
        String phone = loginForm.getPhone();
        if (RegexUtils.isPhoneInvalid(phone)) {

            return Result.failed("手机号格式错误！");
        }

        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);
        String code = loginForm.getCode();
        if (cacheCode == null || !cacheCode.equals(code)) {
            return Result.failed("验证码错误");
        }

        if (query().eq("phone_number", phone).one()!=null){
            return Result.failed("您的账户已存在，请直接登录");
        }
        if (query().eq("nick_name", phone).one()!=null){
            return Result.failed("该昵称已存在，请重新输入");
        }
        String token = UUID.randomUUID().toString(true);
        Member member = new Member();
        member.setMemberId(0);
        member.setCreateTime(DateUtil.now());
        member.setPassword(loginForm.getPassword());
        member.setNickName(loginForm.getNickName());
        member.setPhoneNumber(loginForm.getPhone());
        this.saveOrUpdate(member);
        UserDto userDto = new UserDto();
        userDto.setRole("member");
        userDto.setUserId(query().eq("phone_number", phone).one().getMemberId());
        userDto.setNickName(member.getNickName());
        if (member.getPicture() == null)
            userDto.setPicture("null");
        else
            userDto.setPicture(member.getPicture());
//        UserDto userDto = BeanUtil.copyProperties(member, UserDto.class);

        Map<String, Object> userMap = BeanUtil.beanToMap(userDto, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
        String tokenKey = LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);

        return Result.success(userDto);
    }
}
