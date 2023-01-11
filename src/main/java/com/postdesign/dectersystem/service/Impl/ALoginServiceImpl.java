package com.postdesign.dectersystem.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.postdesign.dectersystem.entity.User;
import com.postdesign.dectersystem.mapper.UserMapper;
import com.postdesign.dectersystem.service.ALoginService;

import java.util.Map;

/**
 * 实现登录业务
 */

public class ALoginServiceImpl extends ServiceImpl<UserMapper, User> implements ALoginService {

    @Override
    public Map<String, Object> login(String uname, String password) {
        return null;
    }
}
