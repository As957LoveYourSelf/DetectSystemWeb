package com.postdesign.detectsystem.controller.backstage;

import com.postdesign.detectsystem.service.backstageService.TeacherManageService;
import com.postdesign.detectsystem.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacherManagePage")
public class TeacherManageController {

    @Autowired
    TeacherManageService teacherManageService;

    @RequestMapping("/getCollegeSelector")
    @ResponseBody
    JSONResult<List<String>> getCollegeSelector(){
        List<String> collegeSelector = teacherManageService.getCollegeSelector();
        return new JSONResult<>(collegeSelector);
    }
    @RequestMapping("/selectTeacher")
    @ResponseBody
    JSONResult<List<Map<String, Object>>> select(String tno, String tname, String college){
        List<Map<String, Object>> maps = teacherManageService.select(tno, tname, college);
        return new JSONResult<>(maps);
    }

    @RequestMapping("/getTeacherDetail")
    @ResponseBody
    JSONResult<Map<String, Object>> getTeacherDetail(String tno){
        Map<String, Object> teacherDetail = teacherManageService.getTeacherDetail(tno);
        return new JSONResult<>(teacherDetail);
    }

    @RequestMapping("/removeTeacher")
    @ResponseBody
    JSONResult<String> removeTeacher(String tno){
        String s = teacherManageService.removeTeacher(tno);
        return new JSONResult<>(s);
    }

}
