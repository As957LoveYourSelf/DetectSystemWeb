package com.postdesign.dectersystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.postdesign.dectersystem.entity.User;

import java.util.Map;

/**
 * 定义需要实现的业务
 */
public interface ALoginService extends IService<User> {
    Map<String, Object> login(String uname, String password);

}
