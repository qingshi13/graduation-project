package com.gymms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gymms.entity.Appointment;
import com.gymms.entity.dto.CoachMemberDto;
import org.springframework.stereotype.Service;

public interface AppointmentService extends IService<Appointment> {
    Page<Appointment> getAppointment(Page<Appointment> page, Integer coachId);
}
