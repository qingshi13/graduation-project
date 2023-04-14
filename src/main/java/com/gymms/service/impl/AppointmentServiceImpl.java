package com.gymms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gymms.entity.Appointment;
import com.gymms.entity.dto.CoachMemberDto;
import com.gymms.mapper.AppointmentMapper;
import com.gymms.service.AppointmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {

    @Resource
    private AppointmentMapper appointmentMapper;

    @Override
    public Page<Appointment> getAppointment(Page<Appointment> page, Integer coachId) {
        return appointmentMapper.getAppointment(page,coachId);
    }
}
