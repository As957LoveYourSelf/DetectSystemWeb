package com.postdesign.dectersystem.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.postdesign.dectersystem.entity.User;
import com.postdesign.dectersystem.mapper.UserMapper;
import com.postdesign.dectersystem.service.ARegisterService;
import com.postdesign.dectersystem.utils.CodeUtil;
import com.postdesign.dectersystem.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * 业务实现：
 * 1、 验证注册的用户是否已存在
 * 2、 发送邮件至用户邮箱进行验证(点击链接验证)
 * 3、 将验证通过后的用户信息存入数据库
 * 用户状态state：
 * 0：用户存在，但未经邮箱验证，不予登录
 * 1：普通正常用户
 * Online:
 * 0为离线，1为在线
 */


@Service
public class ARegisterServiceImpl extends ServiceImpl<UserMapper, User> implements ARegisterService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public Map<String, Object> register(String uno, String uname, String psd, String email) {
        User user = findUser(uname);
        Map<String, Object> msgMap = new HashMap<>();
        if (existEmail(email)){
            msgMap.put("registerState", "mailExist");
            return msgMap;
        }
        if (user != null){
            if (user.getState() == 0){
                msgMap.put("registerState", "userMailUnCheck");
            }
            else if (user.getState() == 1){
                msgMap.put("registerState", "userExist");
            }
        }
        else {
            User userN = createInfo(uno, uname, psd, email);
            if (postCheckContext2Email(userN)){
                msgMap.put("registerState", "waitCheckEmail");
            }
            else {
                msgMap.put("registerState", "emailSendErr");
            }
        }
        return msgMap;
    }

    @Override
    public Map<String, Object> checkCode(String uname, String code){
        Map<String, Object> msgMap = new HashMap<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uname", uname);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null && user.getRcode().equals(code)){
            msgMap.put("checkResult", "ok");
        }else {
            msgMap.put("checkResult", "fail");
        }
        return msgMap;
    }

    private void insertInfo(User user) {
    }

    private boolean postCheckContext2Email(User user) {
        CodeUtil codeUtil = new CodeUtil();
        String code = codeUtil.generateCode();
        user.setRcode(code);
        user.setState((byte) 0);
        insertInfo(user);
        String text = "<p>你好!"+user.getUname()+"</p><p>请点击<a href='http://localhost:8080/registerPage/activeEmail?uname='>"+user.getUname()+"&code="+code+"完成注册</a></p>";
        return MailUtil.sendMail(user.getEmail(), text, "注册邮箱激活");
    }

    private User createInfo(String uno, String uname, String psd, String email){
        User user = new User();
        user.setUno(uno);
        user.setUname(uname);
        user.setPassword(psd);
        user.setOnline((byte) 0);
        user.setEmail(email);
        return user;
    }

    private int getUserState(User user){
        return user.getState();
    }

    private Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    private User findUser(String uname){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uname", uname);
        return userMapper.selectOne(queryWrapper);
    }

    private boolean existEmail(String email){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return userMapper.selectOne(queryWrapper) != null;
    }
}
