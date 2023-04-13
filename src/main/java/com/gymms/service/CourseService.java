package com.gymms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gymms.entity.Course;

import java.util.List;

public interface CourseService  extends IService<Course> {
    Course findOneByCourseId(Integer id);

    List<Course> findByCourseNature(Integer id);

    Integer getSum(Integer courseId);

    Integer getNum(Integer courseId);
}
