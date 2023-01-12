package com.postdesign.dectersystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.postdesign.dectersystem.entity.User;

import java.util.Map;

public interface ARegisterService extends IService<User> {
    Map<String, Object> register(String uno, String uname, String psd, String email);
    public Map<String, Object> checkCode(String uname, String code);
}
