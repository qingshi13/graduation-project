package com.gymms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gymms.entity.Course;
import com.gymms.mapper.CourseMapper;
import com.gymms.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Resource
    private CourseMapper courseMapper;

    @Override
    public Course findOneByCourseId(Integer id) {
        return courseMapper.findOneByCourseId(id);
    }

    @Override
    public List<Course> findByCourseNature(Integer id) {
        return courseMapper.findByCourseNature(id);
    }

    @Override
    public Integer getSum(Integer courseId) {
        return courseMapper.getSum(courseId);
    }

    @Override
    public Integer getNum(Integer courseId) {
        return courseMapper.getNum(courseId);
    }

    @Override
    public Page<Course> getallcourses(Page<Course> Page, String name, String courseNature, String state) {
        return courseMapper.getallcourses(Page,name,courseNature,state);
    }
}
