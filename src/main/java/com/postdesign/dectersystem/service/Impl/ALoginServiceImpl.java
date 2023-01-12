package com.postdesign.dectersystem.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.postdesign.dectersystem.entity.User;
import com.postdesign.dectersystem.mapper.UserMapper;
import com.postdesign.dectersystem.service.ALoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现登录业务
 */
@Service
public class ALoginServiceImpl extends ServiceImpl<UserMapper, User> implements ALoginService {

    @Autowired(required = false)
    private UserMapper userMapper;
    @Override
    public Map<String, Object> login(String uname, String password) {
        Map<String, Object> msgMap = new HashMap<>();
        User user = checkUser(uname);
        if (user != null){
            if (checkPassword(user, password)){
                msgMap.put("loginState", "success");
            }
            else {
                msgMap.put("loginState", "psdUnCheck");
            }
        }
        else {
            msgMap.put("loginState", "unameUnCheck");
        }
        return msgMap;
    }

    private boolean checkPassword(User user, String psd){
        return user.getPassword().equals(psd);
    }

    private User checkUser(String uname){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uname", uname);
        return userMapper.selectOne(queryWrapper);
    }
}
