package com.postdesign.detectsystem.controller.currency;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 课表管理
 * 手机端可以查询个人课表、班级课表
 * 后台导入课程数据（通过班级课表）
 * */
@Controller
@RequestMapping(value = "/timeTableManage")
public class TimetableController {

    /**
     * From Android
     * 查询个人课表
     * */
    @ResponseBody
    @RequestMapping(value = "/searchPersonTable")
    public String searchPersonTable(String uid){
        return null;
    }


    /**
     * 个人课表下载
     * */
    @ResponseBody
    @RequestMapping("/personTableDownload")
    public String personTableDownload(){
        return null;
    }


    /**
     * From Android
     * 查询班级课表
     * */
    @ResponseBody
    @RequestMapping(value = "/searchClassTable")
    public String searchClassTable(String clsid){
        return null;
    }
    /**
     * 班级课表下载
     */
    @ResponseBody
    @RequestMapping("classTableDownload")
    public String clsTableDownload(){
        return null;
    }

    /**
     * From Backstage
     * 导入课表（excel/文本）
     * */
    @ResponseBody
    @RequestMapping(value = "/loadTable")
    public String loadTable(){
        return null;
    }
}
