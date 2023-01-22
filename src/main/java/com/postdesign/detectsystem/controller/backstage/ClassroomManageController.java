package com.postdesign.detectsystem.controller.backstage;

import com.postdesign.detectsystem.service.backstageService.ClassroomManageService;
import com.postdesign.detectsystem.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/classroomManage")
public class ClassroomManageController {
    @Autowired
    ClassroomManageService classroomManageService;

    @RequestMapping("/getBuildingInfo")
    @ResponseBody
    JSONResult<List<String>> getBuildingInfo(){
        List<String> msg = classroomManageService.getBuildingInfo();
        return new JSONResult<>(msg);
    }
    @RequestMapping("getBuildingFloor")
    @ResponseBody
    JSONResult<Integer> getBuildingFloor(String buildingName){
        Integer floor = classroomManageService.getBuildingFloor(buildingName);
        return new JSONResult<>(floor);
    }
}
