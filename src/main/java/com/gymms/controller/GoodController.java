package com.gymms.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gymms.entity.Course;
import com.gymms.entity.Good;
import com.gymms.entity.Member;
import com.gymms.service.GoodService;
import com.gymms.service.impl.GoodServiceImpl;
import com.gymms.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/good")
public class GoodController{
    @Resource
    private GoodService goodService;

    @GetMapping("/page")
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

    @GetMapping("/getByGoodId/{id}")
    public Result findOne(@PathVariable Integer id) {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("good_id",id);
        Good one = goodService.getOne(queryWrapper);
        return Result.success(one);
    }

    /**
     * 新增或更新
     * @param
     */
    @PostMapping("/save")
    public Result save(@RequestBody Good good){

        if (good.getName()==null||good.getDescription()==null||good.getStore()==null
                ||good.getPicture()==null||good.getPrice()==null){
            return Result.failed("请填完所有信息");
        }
        if(good.getGoodId()==null){
            good.setGoodId(0);
            good.setCreateTime(DateUtil.now());
            return Result.success(goodService.saveOrUpdate(good));
        }else {
            return Result.success(goodService.updateById(good));
        }

    }
}
