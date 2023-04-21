package com.gymms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gymms.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;


@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    Page<Course> page(Page<Course> page, @Param("id") Integer id, @Param("name") String name);

    Course findOneByCourseId(@Param("id") Integer id);

    ArrayList<Course> findByCourseNature(@Param("id") Integer id);

    Integer getSum(@Param("courseId") Integer courseId);

    Integer getNum(@Param("courseId") Integer courseId);

    Page<Course> getallcourses(Page<Course> page,@Param("name") String name,@Param("courseNature") String courseNature,@Param("state") String state);
}
