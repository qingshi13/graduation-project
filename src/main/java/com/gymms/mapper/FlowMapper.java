package com.gymms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gymms.entity.Flow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface FlowMapper extends BaseMapper<Flow> {
    Integer getweek(@Param("weekdate") String weekdate);
}
