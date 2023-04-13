package com.gymms.service.impl;

import com.gymms.entity.Admin;
import com.gymms.mapper.AdminMapper;
import com.gymms.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    @Override
    public List<Admin> getAllAdmin() {
        List<Admin> alladmins = adminMapper.getAllAdmin();
        return alladmins;
    }

//    @Override
//    public Admin findByAdminId(Integer id) {
//        return null;
//    }
}
