package com.postdesign.detectsystem.controller.backstage;


import com.postdesign.detectsystem.service.backstageService.ClassMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 *  班级管理
 *  1、班级列表显示
 *  2、班级成员显示
 *  3、班级情况变更（学生转出转入、任课老师变动）
 * */
@Controller
@RequestMapping(value = "/classMangerPage")
public class ClassManageController {

    @Autowired
    ClassMangerService classMangerService;

    @ResponseBody
    @RequestMapping(value = "/get_cls_list")
    public String getClassList(){
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/get_cls_info")
    public String getClassInfo(String clsName){
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/cls_info_change")
    public String clsInfoChange(String uid, String opType){
        return null;
    }
}
