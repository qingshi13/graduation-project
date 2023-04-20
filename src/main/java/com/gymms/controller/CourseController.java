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
@RequestMapping("/course")
public class CourseController {
    @Resource
    private CourseService courseService;

    @Resource
    private CourseMapper courseMapper;

    /**
     * 新增或更新
     * @param
     */
    @PostMapping("/save")
    public Result save(@RequestBody Course course){
        course.setCourseId(0);
        course.setCreateTime(DateUtil.now());
        if(course.getNum()==null||course.getDescription()==null||course.getName()==null
                ||course.getCourseNature()==null||course.getPicture()==null){
            return Result.failed("填写不能为空");
        }
        return Result.success(courseService.saveOrUpdate(course));
    }

    @PostMapping("/pass")
    public Result pass(@RequestBody Course course){
        Course course1 = courseService.getById(course.getCourseId());
        course1.setState("已上线");
        return Result.success(courseService.updateById(course1));
    }
    @PostMapping("/fail")
    public Result fail(@RequestBody Course course){

        Course course1 = courseService.getById(course.getCourseId());
        course1.setState("未通过");
        return Result.success(courseService.updateById(course1));
    }

    @GetMapping("/page")
    public Result Page(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer Id,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("course_id");
        queryWrapper.eq("coach_id",Id);
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(courseService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @GetMapping()
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
    @GetMapping("/getByCoachId/{id}")
    public Result findByCoachId(@PathVariable Integer id) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("coach_id",id);
        queryWrapper.eq("state","已上线");
        List<Course> list = courseService.list(queryWrapper);
        return Result.success(list);
    }

    @GetMapping("/getByCourseId/{id}")
    public Result findOneByCourseId(@PathVariable Integer id) {

        return Result.success(courseService.findOneByCourseId(id));
    }

    @GetMapping("/getByCourseNature/{id}")
    public Result findByCourseNature(@PathVariable Integer id) {

        return Result.success(courseService.findByCourseNature(id));
    }

}
