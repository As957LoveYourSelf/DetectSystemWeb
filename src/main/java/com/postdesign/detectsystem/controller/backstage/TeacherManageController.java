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

    @RequestMapping("/selectByCollage")
    @ResponseBody
    JSONResult<List<Map<String, Object>>> selectByCollage(String collage){
        List<Map<String, Object>> maps = teacherManageService.selectByCollage(collage);
        return new JSONResult<>(maps);
    }
    @RequestMapping("/selectByTno")
    @ResponseBody
    JSONResult<List<Map<String, Object>>> selectByTno(String tno) {
        List<Map<String, Object>> maps = teacherManageService.selectByTno(tno);
        return new JSONResult<>(maps);
    }
    @RequestMapping("/selectByTname")
    @ResponseBody
    JSONResult<List<Map<String, Object>>> selectByTname(String tname) {
        List<Map<String, Object>> maps = teacherManageService.selectByTname(tname);
        return new JSONResult<>(maps);
    }
    @RequestMapping("/selectByCollageAndTno")
    @ResponseBody
    JSONResult<List<Map<String, Object>>> selectByCollageAndTno(String collage, String tno){
        List<Map<String, Object>> maps = teacherManageService.selectByCollageAndTno(collage, tno);
        return new JSONResult<>(maps);
    }
    @RequestMapping("/selectByCollageAndTname")
    @ResponseBody
    JSONResult<List<Map<String, Object>>> selectByCollageAndTname(String collage, String tname){
        List<Map<String, Object>> maps = teacherManageService.selectByCollageAndTname(collage, tname);
        return new JSONResult<>(maps);
    }
    @RequestMapping("/selectByTnoAndTName")
    @ResponseBody
    JSONResult<List<Map<String, Object>>> selectByTnoAndTName(String tno, String tname){
        List<Map<String, Object>> maps = teacherManageService.selectByTnoAndTName(tno, tname);
        return new JSONResult<>(maps);
    }
    @RequestMapping("/selectByTnoAndTNameAndCollage")
    @ResponseBody
    JSONResult<List<Map<String, Object>>> selectByTnoAndTNameAndCollage(String tno, String tname, String collage){
        List<Map<String, Object>> maps = teacherManageService.selectByTnoAndTNameAndCollage(tno, tname, collage);
        return new JSONResult<>(maps);
    }

    @RequestMapping("/getTeacherDetail")
    @ResponseBody
    JSONResult<Map<String, Object>> getTeacherDetail(String tno){
        Map<String, Object> teacherDetail = teacherManageService.getTeacherDetail(tno);
        return new JSONResult<>(teacherDetail);
    }

}
