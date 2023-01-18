package com.postdesign.detectsystem.controller.currency;


import com.postdesign.detectsystem.service.currencyService.RegisterService;
import com.postdesign.detectsystem.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/registerPage")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping("/register")
    @ResponseBody
    public String register(String uno, String uname, String psd, String email, String type){
        Map<String, Object> msgMap = registerService.register(uno, uname, psd, email, type);
        JsonUtil<Map<String, Object>> jsonUtil = new JsonUtil<>();
        return jsonUtil.ObjToJson(msgMap);
    }


    @RequestMapping(value = "/activeEmail")
    @ResponseBody
    public String checkCode(String uname, String code){
        Map<String, Object> msgMap = registerService.checkCode(uname, code);
        JsonUtil<Map<String, Object>> jsonUtil = new JsonUtil<>();
        return jsonUtil.ObjToJson(msgMap);
    }
}
