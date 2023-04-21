package com.gymms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gymms.entity.Course;

import java.util.ArrayList;

public interface CourseService  extends IService<Course> {
    Course findOneByCourseId(Integer id);

    ArrayList<Course> findByCourseNature(Integer id);

    Integer getSum(Integer courseId);

    Integer getNum(Integer courseId);

    Page<Course> getallcourses(Page<Course> Page, String name, String courseNature, String state);
}
