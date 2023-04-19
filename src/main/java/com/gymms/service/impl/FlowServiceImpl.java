package com.gymms.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gymms.entity.Appointment;
import com.gymms.entity.Flow;
import com.gymms.mapper.FlowMapper;
import com.gymms.service.FlowService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;

@Service
public class FlowServiceImpl extends ServiceImpl<FlowMapper, Flow> implements FlowService {
    @Override
    public ArrayList<Integer> getflow(String date) {
        ArrayList<Integer> num = new ArrayList<>();

        QueryWrapper<Flow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date",date);
        queryWrapper.eq("time","上午");
        num.add(this.getOne(queryWrapper) == null ? 0 : this.getOne(queryWrapper).getNum());

        QueryWrapper<Flow> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("date",date);
        queryWrapper1.eq("time","下午");
        num.add(this.getOne(queryWrapper1) == null ? 0 : this.getOne(queryWrapper1).getNum());

        QueryWrapper<Flow> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("date",date);
        queryWrapper2.eq("time","晚上");
        num.add(this.getOne(queryWrapper2) == null ? 0 : this.getOne(queryWrapper2).getNum());

        Date datetime = DateUtil.parse(date,"yyyy-MM-dd");
        String tomorrow = DateUtil.offsetDay(datetime,1).toString().substring(0, 10);
        String aftertomorrow = DateUtil.offsetDay(datetime,2).toString().substring(0, 10);

        QueryWrapper<Flow> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("date",tomorrow);
        queryWrapper3.eq("time","上午");
        num.add(this.getOne(queryWrapper3) == null ? 0 : this.getOne(queryWrapper3).getNum());

        QueryWrapper<Flow> queryWrapper4 = new QueryWrapper<>();
        queryWrapper4.eq("date",tomorrow);
        queryWrapper4.eq("time","下午");
        num.add(this.getOne(queryWrapper4) == null ? 0 : this.getOne(queryWrapper4).getNum());

        QueryWrapper<Flow> queryWrapper5 = new QueryWrapper<>();
        queryWrapper5.eq("date",tomorrow);
        queryWrapper5.eq("time","晚上");
        num.add(this.getOne(queryWrapper5) == null ? 0 : this.getOne(queryWrapper5).getNum());

        QueryWrapper<Flow> queryWrapper6 = new QueryWrapper<>();
        queryWrapper6.eq("date",aftertomorrow);
        queryWrapper6.eq("time","上午");
        num.add(this.getOne(queryWrapper6) == null ? 0 : this.getOne(queryWrapper6).getNum());

        QueryWrapper<Flow> queryWrapper7 = new QueryWrapper<>();
        queryWrapper7.eq("date",aftertomorrow);
        queryWrapper7.eq("time","下午");
        num.add(this.getOne(queryWrapper7) == null ? 0 : this.getOne(queryWrapper7).getNum());

        QueryWrapper<Flow> queryWrapper8 = new QueryWrapper<>();
        queryWrapper8.eq("date",aftertomorrow);
        queryWrapper8.eq("time","晚上");
        num.add(this.getOne(queryWrapper8) == null ? 0 : this.getOne(queryWrapper8).getNum());

        return num;
    }
}
