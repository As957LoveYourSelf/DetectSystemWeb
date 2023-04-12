package com.postdesign.detectsystem.service.serviceImpl.androidImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.postdesign.detectsystem.entity.User;
import com.postdesign.detectsystem.mapper.*;
import com.postdesign.detectsystem.service.androidService.UserMangerService;
import com.postdesign.detectsystem.service.currencyService.RedisService;
import com.postdesign.detectsystem.utils.CodeUtil;
import com.postdesign.detectsystem.utils.MailUtil;
import com.postdesign.detectsystem.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserMangerServiceImpl implements UserMangerService {

    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired(required = false)
    RedisService redisService;

    @Override
    public Map<String, Object> changeInfo(String uid, Map<String, Object> newInfo) {
        Map<String ,Object> info = new HashMap<>();
        try {
            User user = userMapper.selectById(uid);
            user.setSex((String) newInfo.get("sex"));
            user.setEmail((String) newInfo.get("email"));
            user.setUage((Integer) newInfo.get("age"));
            user.setUphone((String) newInfo.get("phone"));
            user.setIntroduce((String) newInfo.get("introduce"));
            userMapper.updateById(user);
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
                    redisService.set(uid+"NEWPSD", newPassword);
                    info.put("status", "identifyEmail");
                }else {
                    info.put("status", "postEmailError");
                }
            }
        }
        return info;
    }

    @Override
    public boolean changeConfirm(String uid, String code) {
        if (redisService.get(uid) != null && redisService.get(uid).equals(code)){
            String newpsd = (String) redisService.get(uid+"NEWPSD");
            User user = userMapper.selectById(uid);
            user.setPassword(newpsd);
            userMapper.updateById(user);
            redisService.delete(uid+"NEWPSD");
            return true;
        }
        return false;
    }

    @Override
    public String resetPsd(String uid) {
        try {
            User user = userMapper.selectById(uid);
            String s = Md5Util.encodeByMd5("123456");
            user.setPassword(s);
            userMapper.updateById(user);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

    @Override
    public String resetFaceImport(String uid) {
        try {
            User user = userMapper.selectById(uid);
            user.setAddfacecount(0);
            userMapper.updateById(user);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }


    private User selectByuid(String uid){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        return userMapper.selectOne(queryWrapper);
    }

    private boolean postCheckContext2Email(User user) {
        CodeUtil codeUtil = new CodeUtil();
        String code = codeUtil.generateCode();
        redisService.set(user.getUid(), code);
        String text = "<p>你好!"+user.getUname()+"</p><p>请点击<a href='http://192.168.0.4:8066/userManagePage/psdConfirm?uid="+user.getUid()+"&code="+code+"'>完成密码修改确认</a></p>";
        return MailUtil.sendMail(user.getEmail(), text, "密码修改确认");
    }
}

