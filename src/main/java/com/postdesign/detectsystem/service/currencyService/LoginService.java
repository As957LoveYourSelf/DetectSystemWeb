package com.postdesign.detectsystem.service.currencyService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.postdesign.detectsystem.entity.User;

import java.util.Map;

/**
 * 定义需要实现的业务
 */
public interface LoginService{
    Map<String, Object> login(String uname, String password);
    Map<String, Object> loginByID(String id, String password);
}
