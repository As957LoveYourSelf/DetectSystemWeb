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
    public JSONResult<Map<String, Object>> changeInfo(@RequestBody Map<String, Object> info){
        User newUser = (User) info.get("newInfo");
        Map<String, Object> response = userMangerService.changeInfo(newUser);
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



}
