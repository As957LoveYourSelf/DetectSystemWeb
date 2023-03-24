package com.postdesign.detectsystem.controller.android;

import com.postdesign.detectsystem.entity.User;
import com.postdesign.detectsystem.service.androidService.UserMangerService;
import com.postdesign.detectsystem.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


@Controller
@RequestMapping("/userManagePage")
public class UserManageController {

    @Autowired
    UserMangerService userMangerService;

    @RequestMapping("/changInfo")
    @ResponseBody
    public JSONResult<Map<String, Object>> changeInfo(String uid,@RequestBody Map<String, Object> info){
        Map<String, Object> response = userMangerService.changeInfo(uid, info);
        return new JSONResult<>(response);
    }


    @RequestMapping("/changePassword")
    @ResponseBody
    public JSONResult<Map<String, Object>> changePassword(@RequestBody Map<String, String> info){
        String newPsd = info.get("newPsd");
        String uid = info.get("uid");
        Map<String, Object> response = userMangerService.changePassword(uid, newPsd);
        return new JSONResult<>(response);
    }
    //TODO:将验证码设置在Redis里面 可以设置过期时间
    @RequestMapping("/psdConfirm")
    public String psdConfirm(String uid, String code){
        System.out.println(uid+code);
        boolean b = userMangerService.changeConfirm(uid, code);
        if (b){
            return "codecheck_success";
        }
        return "codecheck_error";
    }
}
