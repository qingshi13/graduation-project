package com.gymms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gymms.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    List<Admin> getAllAdmin();
//    Admin findByAdminId(Integer id);

}
