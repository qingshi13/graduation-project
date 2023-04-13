package com.gymms.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gymms.entity.Coach;
import com.gymms.entity.Course;
import com.gymms.entity.Member;
import com.gymms.entity.dto.LimitDto;
import com.gymms.service.CoachService;
import com.gymms.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class CoachController {

    @Resource
    private CoachService coachService;

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
        QueryWrapper<Coach> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("coach_id",coachId);
        Coach one = coachService.getOne(queryWrapper);
        return Result.success(one);
    }

    /**
     * 新增或更新
     * @param
     */
    @PostMapping("/coach/save")
    public Result save(@RequestBody Coach coach){
        Integer coachId = coach.getCoachId();
        if (StrUtil.isBlank((coachId).toString())){
            return Result.validateFailed( "参数错误");
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
                        @RequestParam Integer pageNum,
                        @RequestParam Integer pageSize){

        return Result.success(coachService.getStudent(new Page<>(pageNum, pageSize), coachId));
    }

}
