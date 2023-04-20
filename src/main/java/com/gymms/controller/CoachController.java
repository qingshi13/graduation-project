package com.gymms.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gymms.entity.Coach;
import com.gymms.entity.Course;
import com.gymms.entity.Member;
import com.gymms.entity.Recharge;
import com.gymms.entity.dto.LimitDto;
import com.gymms.service.CoachService;
import com.gymms.service.RechargeService;
import com.gymms.util.RegexUtils;
import com.gymms.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class CoachController {

    @Resource
    private CoachService coachService;
    @Resource
    private RechargeService rechargeService;


    @GetMapping("/coach")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String position,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Coach> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("coach_id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        if (!"".equals(position)) {
            queryWrapper.like("position", position);
        }
        return Result.success(coachService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    /**
     * 根据id查教练
     * @param coachId
     */
    @GetMapping("/coach/getByCoachId/{coachId}")
    public Result getByCoachId(@PathVariable Integer coachId){

        return Result.success(coachService.getById(coachId));
    }

    /**
     * 新增或更新
     * @param
     */
    @PostMapping("/coach/save")
    public Result save(@RequestBody Coach coach){
        Integer coachId = coach.getCoachId();
        if (coachId == null){
            coach.setCoachId(0);
            if (coach.getNickName()==null||coach.getName()==null||coach.getPhoneNumber()==null||coach.getPicture()==null
            ||coach.getPosition()==null||coach.getDescription()==null){
                return Result.failed("请填完所有信息");
            }
            if (RegexUtils.isPhoneInvalid(coach.getPhoneNumber())) {
                // 2.如果不符合，返回错误信息
                return Result.failed("手机号格式错误！");
            }
            QueryWrapper<Coach> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone_number", coach.getPhoneNumber());
            if (coachService.getOne(queryWrapper)!=null){
                return Result.failed("您的账户已存在，请直接登录");
            }
            QueryWrapper<Coach> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("nick_name", coach.getNickName());
            if (coachService.getOne(queryWrapper1)!=null){
                return Result.failed("该昵称已存在，请重新输入");
            }

            coach.setRemainingSum(0.0);
            coach.setAccumulatePoints(0);
            coach.setMaxpeople(10);
            coach.setCreateTime(DateUtil.now());
            return Result.success(coachService.saveOrUpdate(coach));
        }
        return Result.success(coachService.saveOrUpdate(coach));
    }

    @GetMapping("/coach/limit/{coachId}")
    public Result limit(@PathVariable Integer coachId){

        return Result.success(coachService.limit(coachId));
    }

    @GetMapping("/coach/getstudent")
    public Result getStudent(
                        @RequestParam Integer coachId,
                        @RequestParam(defaultValue = "") String name,
                        @RequestParam Integer pageNum,
                        @RequestParam Integer pageSize){

        return Result.success(coachService.getStudent(new Page<>(pageNum, pageSize), coachId,name));
    }

    @PostMapping("/coach/recharge")
    public Result recharge(@RequestBody Recharge recharge){
        Integer coachId = recharge.getUserId();
        if (StrUtil.isBlank((coachId).toString())){
            return Result.validateFailed( "参数错误");
        }
        if (recharge.getAmount() == null){
            return Result.failed("请输入充值金额");
        }
        Coach coach = coachService.getById(coachId);
        coach.setRemainingSum(recharge.getAmount()+coach.getRemainingSum());
        recharge.setRechargeId(0);
        recharge.setTime(DateUtil.now());
        rechargeService.saveOrUpdate(recharge);
        return Result.success(coachService.saveOrUpdate(coach),"充值成功");
    }

}
