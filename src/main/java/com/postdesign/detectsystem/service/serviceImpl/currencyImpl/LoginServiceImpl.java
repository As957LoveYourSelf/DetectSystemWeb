package com.postdesign.detectsystem.service.serviceImpl.currencyImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.postdesign.detectsystem.entity.User;
import com.postdesign.detectsystem.mapper.UserMapper;
import com.postdesign.detectsystem.service.currencyService.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现登录业务
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Override
    public Map<String, Object> login(String uname, String password) {
        Map<String, Object> msgMap = new HashMap<>();
        User user = checkUser(uname);
        return getInfo(password, user, msgMap);
    }

    @Override
    public Map<String, Object> loginByID(String id, String password) {
        User user = userMapper.selectById(id);
        Map<String, Object> msgMap = new HashMap<>();
        return getInfo(password, user, msgMap);
    }

    private Map<String, Object> getInfo(String password, User user, Map<String, Object> msgMap) {
        if (user != null){
            if (checkPassword(user, password)){
                msgMap.put("loginState", "success");
                msgMap.put("userInfo",user);
            }
            else {
                msgMap.put("loginState", "psdUnCheck");
                msgMap.put("userInfo",null);
            }
        }
        else {
            msgMap.put("loginState", "unameUnCheck");
            msgMap.put("userInfo",null);
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
