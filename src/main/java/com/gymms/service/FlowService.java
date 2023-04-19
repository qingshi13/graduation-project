package com.gymms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gymms.entity.Flow;

import java.util.ArrayList;

public interface FlowService extends IService<Flow> {
    ArrayList<Integer> getflow(String date);
}
