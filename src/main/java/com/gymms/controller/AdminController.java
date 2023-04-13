package com.gymms.controller;

import com.gymms.entity.Admin;
import com.gymms.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.List;

@Controller
@CrossOrigin
public class AdminController {
    @Resource
    private AdminService adminService;

    @GetMapping("/getAllAdmin")
    @ResponseBody
    public List<Admin> getAll(){
        List<Admin> admins = adminService.getAllAdmin();
        return admins;
    }
}
