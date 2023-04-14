package com.gymms.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gymms.entity.Appointment;
import com.gymms.entity.Course;
import com.gymms.service.AppointmentService;
import com.gymms.service.CourseService;
import com.gymms.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Resource
    private AppointmentService appointmentService;
    @Resource
    private CourseService courseService;

    @PostMapping
    public Result save(@RequestBody Appointment appointment) {
        appointment.setAppointmentId(0);
        Date datetime = DateUtil.parse(appointment.getDate(),"yyyy-MM-dd HH:mm:ss");

        if ( DateUtil.tomorrow().isAfterOrEquals(datetime) ){
            return Result.failed("请预约一天后的时间段");
        }

        String date = appointment.getDate().substring(0, 10);
        String point = appointment.getDate().substring(11, 19);
        String time = appointment.getDate().substring(11, 13);
        if (Integer.parseInt(time) >= 8 && Integer.parseInt(time)<=11){
            time = "上午";
        }else if (Integer.parseInt(time) >= 13 && Integer.parseInt(time)<=16){
            time = "下午";

        }else if (Integer.parseInt(time) >= 18 && Integer.parseInt(time)<=21){
            time = "晚上";

        }else {
            return Result.failed("请在规定时间内预约");
        }
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setPoint(point);
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq( "course_id",appointment.getCourseId());
        int coachId = courseService.getOne(courseQueryWrapper).getCoachId();
        appointment.setCoachId(coachId);

        QueryWrapper<Appointment> appointmentQueryWrapper = new QueryWrapper<>();
        appointmentQueryWrapper.eq( "date",appointment.getDate());
        appointmentQueryWrapper.eq( "coach_id",appointment.getCoachId());
        appointmentQueryWrapper.eq( "time",appointment.getTime());
        Appointment appointment1 = appointmentService.getOne(appointmentQueryWrapper);

        if (appointment1 != null){
            return Result.failed("该时间段已经有人预约，请换个时间段");
        }

        return Result.success(appointmentService.saveOrUpdate(appointment),"预约成功");
    }

    @GetMapping("/page")
    public Result getAppointment(
            @RequestParam Integer coachId,
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize){

        return Result.success(appointmentService.getAppointment(new Page<>(pageNum, pageSize), coachId));
    }
}
