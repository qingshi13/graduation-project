package com.gymms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gymms.entity.Course;

import java.util.List;

public interface CourseService  extends IService<Course> {
    Course findOneByCourseId(Integer id);

    List<Course> findByCourseNature(Integer id);

    Integer getSum(Integer courseId);

    Integer getNum(Integer courseId);

    Page<Course> getallcourses(Page<Course> Page, String name, String courseNature, String state);
}
