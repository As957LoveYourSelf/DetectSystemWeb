package com.postdesign.detectsystem.controller.backstage;

import com.postdesign.detectsystem.service.backstageService.StudentManageService;
import com.postdesign.detectsystem.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/studentManagePage")
public class StudentManageController {

    @Autowired
    StudentManageService studentManageService;

    @RequestMapping("/selectBysno")
    @ResponseBody
    JSONResult<List<Map<String, Object>>> selectBySno(String sno){
        List<Map<String, Object>> msg = studentManageService.selectBySno(sno);
        return new JSONResult<>(msg);
    }
    @RequestMapping("/selectBysname")
    @ResponseBody
    JSONResult<List<Map<String, Object>>> selectBySname(String sname){
        List<Map<String, Object>> msg = studentManageService.selectBySname(sname);
        return new JSONResult<>(msg);
    }
    @RequestMapping("/selectBysnoAndsname")
    @ResponseBody
    JSONResult<List<Map<String, Object>>> selectBySNoAndSName(String sno, String sname){
        List<Map<String, Object>> msg = studentManageService.selectBySNoAndSName(sno,sname);
        return new JSONResult<>(msg);
    }

    @RequestMapping("/getStudentDetail")
    @ResponseBody
    JSONResult<Map<String, Object>> getStudentDetail(String sno){
        Map<String, Object> msg = studentManageService.getStudentDetail(sno);
        return new JSONResult<>(msg);
    }

}
