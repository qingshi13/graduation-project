package com.gymms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gymms.entity.Course;
import com.gymms.entity.Subscribe;
import com.gymms.mapper.SubscribeMapper;
import com.gymms.service.CourseService;
import com.gymms.service.SubscribeService;
import com.gymms.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RestController
@RequestMapping("/subscribe")
public class SubscribeController {
    @Resource
    private SubscribeService subscribeService;
    @Resource
    private CourseService courseService;
    @Resource
    private SubscribeMapper subscribeMapper;

    @PostMapping()
    public Result subscribe(@RequestBody Subscribe subscribe) {

        return subscribeService.subscribe(subscribe);
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String courseNature,
                           @RequestParam Integer Id,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        return Result.success(subscribeMapper.page(new Page<>(pageNum, pageSize), Id, name, courseNature));
    }

    @PostMapping("/rate")
    public Result rate(@RequestBody Subscribe subscribe) {
        subscribeService.updateById(subscribe);

        Course course = courseService.getById(subscribe.getCourseId());
        Integer sum = courseService.getSum(subscribe.getCourseId());
        Integer num = courseService.getNum(subscribe.getCourseId());

        double res = new BigDecimal((double) sum / num).setScale(2, RoundingMode.HALF_UP).doubleValue();
        course.setRate(res);
        courseService.updateById(course);
        return Result.success();
    }

}
