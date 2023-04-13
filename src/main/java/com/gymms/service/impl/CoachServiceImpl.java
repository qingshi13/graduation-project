package com.gymms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gymms.entity.Coach;
import com.gymms.entity.Member;
import com.gymms.entity.dto.LimitDto;
import com.gymms.entity.dto.LoginFormDto;
import com.gymms.entity.dto.UserDto;
import com.gymms.mapper.CoachMapper;
import com.gymms.service.CoachService;
import com.gymms.util.RegexUtils;
import com.gymms.util.Result;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.gymms.util.RedisConstants.*;

@Service
public class CoachServiceImpl extends ServiceImpl<CoachMapper, Coach> implements CoachService {

    @Resource
    private CoachMapper coachMapper;

    @Override
    public LimitDto limit(Integer coachId) {
        Coach coach = query().eq("coach_id",coachId).one();
        LimitDto limitDto = new LimitDto();
        limitDto.setMaxPeople(coach.getMaxpeople());
        limitDto.setSubscribedNum(coachMapper.SubNum(coachId));
        limitDto.setRecruit(coachMapper.Recruit(coachId));
        return limitDto;
    }

}
