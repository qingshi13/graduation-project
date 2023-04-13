package com.gymms.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gymms.entity.*;
import com.gymms.mapper.CartMapper;
import com.gymms.mapper.CourseMapper;
import com.gymms.service.CourseService;
import com.gymms.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CourseController {
    @Resource
    private CourseService courseService;

    @Resource
    private CourseMapper courseMapper;

    /**
     * 新增或更新
     * @param
     */
    @PostMapping("/course/save")
    public Result save(@RequestBody Course course){
        course.setCourseId(0);
        course.setCreateTime(DateUtil.now());
        
        return Result.success(courseService.saveOrUpdate(course));
    }

    @GetMapping("/course/page")
    public Result Page(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer Id,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {

        return Result.success(courseMapper.page(new Page<>(pageNum, pageSize), Id, name));
    }

    @GetMapping("/course")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String courseNature,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("course_id");
        queryWrapper.eq("state","已上线");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        if (!"".equals(courseNature)) {
            queryWrapper.like("course_nature", courseNature);
        }
        return Result.success(courseService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
    @GetMapping("/course/getByCoachId/{id}")
    public Result findByCoachId(@PathVariable Integer id) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("coach_id",id);
        queryWrapper.eq("state","已上线");
        List<Course> list = courseService.list(queryWrapper);
        return Result.success(list);
    }

    @GetMapping("/course/getByCourseId/{id}")
    public Result findOneByCourseId(@PathVariable Integer id) {

        return Result.success(courseService.findOneByCourseId(id));
    }

    @GetMapping("/course/getByCourseNature/{id}")
    public Result findByCourseNature(@PathVariable Integer id) {

        return Result.success(courseService.findByCourseNature(id));
    }

}
