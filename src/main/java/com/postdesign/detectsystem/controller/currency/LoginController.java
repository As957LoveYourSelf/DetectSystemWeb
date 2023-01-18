package com.postdesign.detectsystem.controller.currency;


import com.postdesign.detectsystem.service.currencyService.LoginService;
import com.postdesign.detectsystem.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/loginPage")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    @ResponseBody
    public String login(String uname, String psd){
//        System.out.println(uname+": "+psd);
        Map<String, Object> msgMap = loginService.login(uname, psd);
        JsonUtil<Map<String, Object>> jsonUtil = new JsonUtil<>();
//        System.out.println(jsonUtil.ObjToJson(msgMap));
        return jsonUtil.ObjToJson(msgMap);
    }
}
