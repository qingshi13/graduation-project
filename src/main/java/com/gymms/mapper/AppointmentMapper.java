package com.gymms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gymms.entity.Appointment;
import com.gymms.entity.dto.CoachMemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AppointmentMapper extends BaseMapper<Appointment> {
    Page<Appointment> getAppointment(Page<Appointment> page,@Param("coachId") Integer coachId);
}
