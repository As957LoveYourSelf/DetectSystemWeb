package com.postdesign.detectsystem.controller.currency;


import com.postdesign.detectsystem.service.currencyService.LoginService;
import com.postdesign.detectsystem.utils.JSONResult;
import com.postdesign.detectsystem.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/loginPage")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    @ResponseBody
    public JSONResult<Map<String, Object>> login(String uname, String psd){
        // 获取登录判断信息
        Map<String, Object> msgMap = loginService.login(uname, psd);
        // 获取用户token
        if (msgMap.get("loginState").equals("success")){
            String tokenSecret = TokenUtil.getTokenSecret(uname, psd);
            msgMap.put("usertoken", tokenSecret);
        }else {
            msgMap.put("userToken", "");
        }
        return new JSONResult<>(msgMap);
    }
}