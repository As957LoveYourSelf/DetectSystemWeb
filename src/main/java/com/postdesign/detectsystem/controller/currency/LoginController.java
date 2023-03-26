package com.postdesign.detectsystem.controller.currency;


import com.postdesign.detectsystem.service.currencyService.LoginService;
import com.postdesign.detectsystem.service.currencyService.RedisService;
import com.postdesign.detectsystem.utils.JSONResult;
import com.postdesign.detectsystem.utils.Md5Util;
import com.postdesign.detectsystem.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/loginPage")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/login")
    @ResponseBody
    public JSONResult<Map<String, Object>> login(String uname, String psd) throws Exception {
        // 获取登录判断信息
        Map<String, Object> msgMap = loginService.login(uname, psd);
//        System.out.println(msgMap);
        // 获取用户token
        if (msgMap.get("loginState").equals("success")){
            String tokenSecret = Md5Util.encodeByMd5(uname)+"THATISTOKEN"+UUID.randomUUID();
            redisService.set(tokenSecret, tokenSecret);
            msgMap.put("userToken", tokenSecret);
        }else {
            msgMap.put("userToken", "");
        }
        return new JSONResult<>(msgMap);
    }



    @RequestMapping("/applogin")
    @ResponseBody
    public JSONResult<Map<String, Object>> applogin(String id, String psd) throws Exception {
        // 获取登录判断信息
        Map<String, Object> msgMap = loginService.loginByID(id, psd);
//        System.out.println(msgMap);
        // 获取用户token
        if (msgMap != null && msgMap.get("loginState").equals("success")){
            String tokenSecret = Md5Util.encodeByMd5(id)+"THATISTOKEN"+UUID.randomUUID();
            redisService.set(tokenSecret, tokenSecret);
            msgMap.put("usertoken", tokenSecret);
        }else {
            msgMap.put("usertoken", "");
        }
        return new JSONResult<>(msgMap);
    }

    @RequestMapping("/loginout")
    public void loginOut(String uid){
        loginService.loginout(uid);
    }
}