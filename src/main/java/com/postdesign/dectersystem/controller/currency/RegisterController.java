package com.postdesign.dectersystem.controller.currency;


import com.postdesign.dectersystem.service.RegisterService;
import com.postdesign.dectersystem.utils.JsonUtil;
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
    public String register(String uno, String uname, String psd, String email){
        Map<String, Object> msgMap = registerService.register(uno, uname, psd, email);
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
