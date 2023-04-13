package com.gymms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gymms.entity.Coach;
import com.gymms.entity.dto.CoachMemberDto;
import com.gymms.entity.dto.LimitDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CoachMapper extends BaseMapper<Coach> {

    Integer SubNum(@Param("coachId") Integer coachId);

    Integer Recruit(Integer coachId);

    Page<CoachMemberDto> getStudent(Page<CoachMemberDto> page,@Param("coachId") Integer coachId);
}
