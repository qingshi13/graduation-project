package com.gymms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gymms.entity.Subscribe;
import com.gymms.util.Result;

public interface SubscribeService extends IService<Subscribe> {
    Result subscribe(Subscribe subscribe);

}
