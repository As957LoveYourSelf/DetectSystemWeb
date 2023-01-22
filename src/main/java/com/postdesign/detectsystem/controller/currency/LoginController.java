package com.postdesign.detectsystem.controller.currency;


import com.postdesign.detectsystem.service.currencyService.LoginService;
import com.postdesign.detectsystem.utils.JSONResult;
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Map<String, Object>> login(String uname, String psd){
        Map<String, Object> msgMap = loginService.login(uname, psd);
        return new JSONResult<>(msgMap);
    }
}
