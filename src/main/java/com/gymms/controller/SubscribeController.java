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

        return Result.success(subscribeService.subscribe(subscribe),"订阅成功");
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
//        这段代码是将两个整数a和b相除，然后保留两位小数并进行四舍五入。
//        然后将结果转换回double类型并赋值给变量res。
//        这段代码使用了BigDecimal类来进行精确计算，并使用setScale方法设置保留位数和舍入模式（这里使用了四舍五入模式）。
//        最后使用doubleValue方法将BigDecimal类型转换为double类型。
        double res = new BigDecimal((double) sum / num).setScale(2, RoundingMode.HALF_UP).doubleValue();
        course.setRate(res);
        courseService.updateById(course);
        return Result.success();
    }

}
