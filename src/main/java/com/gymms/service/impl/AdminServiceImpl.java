package com.gymms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gymms.entity.Admin;
import com.gymms.mapper.AdminMapper;
import com.gymms.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper,Admin> implements AdminService {
    @Resource
    private AdminMapper adminMapper;

}
