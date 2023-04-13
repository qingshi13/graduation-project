package com.gymms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
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
    private StringRedisTemplate stringRedisTemplate;

//    @Override
//    public List<Member> getAllMember() {
//        List<Member> allmembers = memberMapper.getAllMember();
//        return allmembers;
//    }

    @Override
    public Result sendCode(String phone, HttpSession session) {
        // 1.校验手机号
        if (RegexUtils.isPhoneInvalid(phone)) {
            // 2.如果不符合，返回错误信息
            return Result.failed("手机号格式错误！");
        }
        // 3.符合，生成验证码
        // String code = sendEmail(phone);
        String code = RandomUtil.randomNumbers(4);
        // 4.保存验证码到 session
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + phone, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);
        // 5.发送验证码
        System.out.println(code);
        // 返回ok
        return Result.success();
    }

    @Override
    public Result login(LoginFormDto loginForm, HttpSession session) {
        // 1.校验手机号
        String phone = loginForm.getPhone();
        if (RegexUtils.isPhoneInvalid(phone)) {
            // 2.如果不符合，返回错误信息
            return Result.failed("手机号格式错误！");
        }
        // 3.从redis获取验证码并校验
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);
        String code = loginForm.getCode();
        if (cacheCode == null || !cacheCode.equals(code)) {
            // 不一致，报错
            return Result.failed("验证码错误");
        }

        // 4.一致，根据手机号查询用户 select * from tb_user where phone = ?
        Member member = query().eq("phone_number", phone).one();

        QueryWrapper<Coach> queryWrapper = new QueryWrapper<Coach>().eq("phone_number", phone);
        Coach coach = coachService.getOne(queryWrapper);;

        // 5.判断用户是否存在
        if (member == null && coach == null) {
            return Result.failed("用户不存在");
        }

        System.out.println(coach);
        // 7.保存用户信息到 redis中
        // 7.1.随机生成token，作为登录令牌
        String token = UUID.randomUUID().toString(true);
        // 7.2.将User对象转为HashMap存储


        if (coach == null){
            UserDto userDto = new UserDto();
            userDto.setRole("member");
            userDto.setUserId(member.getMemberId());
            userDto.setNickName(member.getNickName());
            if (member.getPicture() == null)
                userDto.setPicture("null");
            else
                userDto.setPicture(member.getPicture());
//        UserDto userDto = BeanUtil.copyProperties(member, UserDto.class);
            System.out.println(userDto);
            Map<String, Object> userMap = BeanUtil.beanToMap(userDto, new HashMap<>(),
                    CopyOptions.create()
                            .setIgnoreNullValue(true)
                            .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
            // 7.3.存储
            String tokenKey = LOGIN_USER_KEY + token;
            stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
            // 7.4.设置token有效期
            stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);

            // 8.返回token
            return Result.success(userDto);
        }
        else {
            UserDto userDto = new UserDto();
            userDto.setRole("coach");
            userDto.setUserId(coach.getCoachId());
            userDto.setNickName(coach.getNickName());
            if (coach.getPicture() == null)
                userDto.setPicture("null");
            else
                userDto.setPicture(coach.getPicture());
//        UserDto userDto = BeanUtil.copyProperties(member, UserDto.class);
            System.out.println(userDto);
            Map<String, Object> userMap = BeanUtil.beanToMap(userDto, new HashMap<>(),
                    CopyOptions.create()
                            .setIgnoreNullValue(true)
                            .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
            // 7.3.存储
            String tokenKey = LOGIN_USER_KEY + token;
            stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
            // 7.4.设置token有效期
            stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);

            // 8.返回token
            return Result.success(userDto);
        }
    }


}
