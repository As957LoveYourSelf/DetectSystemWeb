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

    @RequestMapping("/getCollageSelector")
    @ResponseBody
    JSONResult<List<String>> getCollageSelector(){
        List<String> collageSelector = teacherManageService.getCollageSelector();
        return new JSONResult<>(collageSelector);
    }
    @RequestMapping("/selectTeacher")
    @ResponseBody
    JSONResult<List<Map<String, Object>>> selectByTnoAndTNameAndCollage(String tno, String tname, String collage){
        List<Map<String, Object>> maps = teacherManageService.select(tno, tname, collage);
        return new JSONResult<>(maps);
    }

    @RequestMapping("/getTeacherDetail")
    @ResponseBody
    JSONResult<Map<String, Object>> getTeacherDetail(String tno){
        Map<String, Object> teacherDetail = teacherManageService.getTeacherDetail(tno);
        return new JSONResult<>(teacherDetail);
    }

}
