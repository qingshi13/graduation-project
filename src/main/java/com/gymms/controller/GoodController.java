package com.gymms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gymms.entity.Good;
import com.gymms.entity.Member;
import com.gymms.service.GoodService;
import com.gymms.service.impl.GoodServiceImpl;
import com.gymms.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Controller
@RestController
public class GoodController{
    @Resource
    private GoodService goodService;

    @GetMapping("/good/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                            @RequestParam Integer pageSize) {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("good_id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(goodService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @GetMapping("/good/getByGoodId/{id}")
    public Result findOne(@PathVariable Integer id) {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("good_id",id);
        Good one = goodService.getOne(queryWrapper);
        return Result.success(one);
    }
}
