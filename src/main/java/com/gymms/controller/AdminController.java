package com.gymms.controller;

import com.gymms.entity.Admin;
import com.gymms.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AdminController {
    @Resource
    private AdminService adminService;


}
