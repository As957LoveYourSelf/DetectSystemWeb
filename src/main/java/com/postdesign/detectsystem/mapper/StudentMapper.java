package com.postdesign.detectsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.postdesign.detectsystem.entity.Students;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends MppBaseMapper<Students> {

}
