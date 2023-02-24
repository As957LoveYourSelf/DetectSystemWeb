package com.postdesign.detectsystem.controller.backstage;

import com.postdesign.detectsystem.service.backstageService.CourseManageService;
import com.postdesign.detectsystem.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/course")
public class CourseManageController {
    @Autowired
    CourseManageService courseManageService;
    @RequestMapping("/import")
    @ResponseBody
    JSONResult<String> importCourseInfo(){
        String s = courseManageService.importCourseInfo();
        return new JSONResult<>(s);
    }
    @RequestMapping("/getGradeSelector")
    @ResponseBody
    JSONResult<List<Integer>> getGradeSelector(){
        List<Integer> gradeSelector = courseManageService.getGradeSelector();
        return new JSONResult<>(gradeSelector);
    }
    @RequestMapping("/getCollageSelector")
    @ResponseBody
    JSONResult<List<String>> getCollageSelector(){
        List<String> collageSelector = courseManageService.getCollageSelector();
        return new JSONResult<>(collageSelector);
    }
    @RequestMapping("/select")
    @ResponseBody
    JSONResult<List<Map<String, Object>>> select(String collage, String major, Integer grade, String type){
        List<Map<String, Object>> select = courseManageService.select(collage, major, grade, type);
        return new JSONResult<>(select);
    }
    @RequestMapping("/deleteCourse")
    @ResponseBody
    JSONResult<String> deleteCourse(String cno, String courseType){
        String s = courseManageService.deleteCourse(cno, courseType);
        return new JSONResult<>(s);
    }
    @RequestMapping("/getCourseTableBySno")
    @ResponseBody
    JSONResult<Map<String, Object>> getCourseTableBySno(String sno){
        Map<String, Object> courseTableBySno = courseManageService.getCourseTableBySno(sno);
        return new JSONResult<>(courseTableBySno);
    }
    @RequestMapping("/getCourseTableByTno")
    @ResponseBody
    JSONResult<Map<String, Object>> getCourseTableByTno(String tno){
        Map<String, Object> courseTableByTno = courseManageService.getCourseTableByTno(tno);
        return new JSONResult<>(courseTableByTno);
    }
}
