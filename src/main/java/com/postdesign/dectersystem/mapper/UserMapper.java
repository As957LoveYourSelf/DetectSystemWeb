package com.postdesign.dectersystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.postdesign.dectersystem.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
