package com.postdesign.detectsystem.controller.backstage;

import com.postdesign.detectsystem.service.backstageService.ClassroomManageService;
import com.postdesign.detectsystem.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/classroomManage")
public class ClassroomManageController {
    @Autowired
    ClassroomManageService classroomManageService;

    @RequestMapping("/getBuildingSelector")
    @ResponseBody
    JSONResult<List<String>> getBuildingSelector(){
        List<String> msg = classroomManageService.getBuildingSelector();
        return new JSONResult<>(msg);
    }
    @RequestMapping("/getBuildingFloorSelector")
    @ResponseBody
    JSONResult<List<Integer>> getBuildingFloor(String buildingName){
        Integer floor = classroomManageService.getBuildingFloor(buildingName);
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= floor; i++) {
            list.add(i);
        }
        return new JSONResult<>(list);
    }

    @RequestMapping("/getClassroomInfo")
    @ResponseBody
    JSONResult<List<Map<String, Object>>> getClassroomInfo(String buildingName, Integer isOrder, Integer floor){
        List<Map<String, Object>> buildingInfo = classroomManageService.getBuildingInfo(buildingName, isOrder, floor);
        return new JSONResult<>(buildingInfo);
    }

    // TODO: 添加用户id，以确认是哪个用户预定了教室
    @RequestMapping("/orderClassroom")
    @ResponseBody
    JSONResult<String> orderClassroom(String clsNo){
        String order = classroomManageService.order(clsNo);
        return new JSONResult<>(order);
    }

    @RequestMapping("/deorderClassroom")
    @ResponseBody
    JSONResult<String> deorderClassroom(String clsNo){
        String deorder = classroomManageService.deorder(clsNo);
        return new JSONResult<>(deorder);
    }

}
