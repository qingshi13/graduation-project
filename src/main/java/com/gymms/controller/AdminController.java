package com.gymms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gymms.entity.Admin;
import com.gymms.entity.Coach;
import com.gymms.entity.Course;
import com.gymms.entity.Member;
import com.gymms.service.AdminService;
import com.gymms.service.AppointmentService;
import com.gymms.service.CourseService;
import com.gymms.service.MemberService;
import com.gymms.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;
    @Resource
    private MemberService memberService;
    @Resource
    private CourseService courseService;
    @Resource
    private AppointmentService appointmentService;

    /**
     * 根据id查管理员
     * @param adminId
     */
    @GetMapping("/getByAdminId/{adminId}")
    public Result getByAdminId(@PathVariable Integer adminId){
        return Result.success(adminService.getById(adminId));
    }

    @GetMapping("/getallmembers")
    public Result getallmembers(@RequestParam(defaultValue = "") String name,
                             @RequestParam(defaultValue = "") String state,
                             @RequestParam Integer pageNum,
                             @RequestParam Integer pageSize) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("member_id");
        if (!"".equals(name)) {
            queryWrapper.like("phone_number", name);
        }
        if (!"".equals(state)) {
            queryWrapper.like("state", state);
        }
        return Result.success(memberService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
    @GetMapping("/getallcourses")
    public Result getallcourses(@RequestParam(defaultValue = "") String name,
                                @RequestParam(defaultValue = "") String state,
                                @RequestParam(defaultValue = "") String courseNature,
                                @RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {

        return Result.success(courseService.getallcourses(new Page<>(pageNum, pageSize),name,courseNature,state));
    }

    @GetMapping("/getallappointments")
    public Result getallappointments(@RequestParam(defaultValue = "") String name,
                                     @RequestParam(defaultValue = "") String state,
                                     @RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize) {

        return Result.success(appointmentService.getallappointments(new Page<>(pageNum, pageSize),name,state));
    }
}
