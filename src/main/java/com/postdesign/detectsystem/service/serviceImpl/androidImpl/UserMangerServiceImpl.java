package com.postdesign.detectsystem.service.serviceImpl.androidImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.postdesign.detectsystem.entity.User;
import com.postdesign.detectsystem.mapper.*;
import com.postdesign.detectsystem.service.androidService.UserMangerService;
import com.postdesign.detectsystem.utils.CodeUtil;
import com.postdesign.detectsystem.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserMangerServiceImpl implements UserMangerService {

    @Autowired(required = false)
    UserMapper userMapper;



    @Override
    public Map<String, Object> changeInfo(User user) {
        Map<String ,Object> info = new HashMap<>();
        try {
            int rows = userMapper.updateById(user);
            System.out.println(rows);
            info.put("status", "success");
        }catch (Exception e){
            info.put("status", "error");
        }
        return info;
    }

    @Override
    public Map<String, Object> changePassword(String uid, String newPassword) {
        Map<String ,Object> info = new HashMap<>();
        // 通过邮箱验证
        User user = selectByuid(uid);
        if (user.getEmail() == null){
            info.put("status", "nonEmail");
        }else {
            if (user.getPassword().equals(newPassword)){
                info.put("status", "psdSame");
            }else {
                // 发送确认信息至邮箱，通过验证即修改密码
                boolean b = postCheckContext2Email(user);
                if (b){
                    info.put("status", "identifyEmail");
                }else {
                    info.put("status", "postEmailError");
                }
            }
        }
        return info;
    }

    @Override
    public Map<String, Object> changeConfirm(String uid, String code) {
        Map<String, Object> info = new HashMap<>();
        User user = selectByuid(uid);
        if (user.getRcode().equals(code)){
            user.setState((byte)1);
            info.put("status", "success");
        }else {
            info.put("status", "error");
        }
        return info;
    }


    private User selectByuid(String uid){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        return userMapper.selectOne(queryWrapper);
    }

    private boolean postCheckContext2Email(User user) {
        CodeUtil codeUtil = new CodeUtil();
        String code = codeUtil.generateCode();
        user.setRcode(code);
        user.setState((byte) 0);
        String text = "<p>你好!"+user.getUname()+"</p><p>请点击<a href='http://localhost:8080/userManagePage/psdComfirm?uid='>"+user.getUid()+"&code="+code+"完成注册</a></p>";
        return MailUtil.sendMail(user.getEmail(), text, "密码修改确认");
    }
}

