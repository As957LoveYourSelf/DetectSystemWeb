package com.postdesign.detectsystem.service.serviceImpl.currencyImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.postdesign.detectsystem.entity.User;
import com.postdesign.detectsystem.mapper.UserMapper;
import com.postdesign.detectsystem.service.currencyService.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现登录业务
 */
@Service
public class LoginServiceImpl implements LoginService {
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
        return this.userMapper.selectOne(queryWrapper);
    }
}
