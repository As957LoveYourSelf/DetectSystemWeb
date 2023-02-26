package com.postdesign.detectsystem.controller.backstage;


import com.postdesign.detectsystem.service.backstageService.ClassMangerService;
import com.postdesign.detectsystem.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


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
    @RequestMapping("/getGradeSelector")
    JSONResult<List<Integer>> getGradeSelector(){
        List<Integer> gradeSelector = classMangerService.getGradeSelector();
        return new JSONResult<>(gradeSelector);
    }
    @ResponseBody
    @RequestMapping("/getCollegeSelector")
    JSONResult<List<String>> getCollegeSelector(){
        List<String> collegeSelector = classMangerService.getCollegeSelector();
        return new JSONResult<>(collegeSelector);
    }
    @ResponseBody
    @RequestMapping("/selectClass")
    JSONResult<List<Map<String, Object>>> select(Integer grade, String college){
        List<Map<String, Object>> maps = classMangerService.select(grade, college);
        return new JSONResult<>(maps);
    }

    @ResponseBody
    @RequestMapping("/getClassDetail")
    JSONResult<Map<String, Object>> getClassDetail(String className){
        Map<String, Object> classInfo = classMangerService.getClassDetail(className);
        return new JSONResult<>(classInfo);
    }
}
