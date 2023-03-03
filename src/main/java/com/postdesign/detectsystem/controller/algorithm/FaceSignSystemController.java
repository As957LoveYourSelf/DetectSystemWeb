package com.postdesign.detectsystem.controller.algorithm;

import com.postdesign.detectsystem.utils.JSONResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class FaceSignSystemController {
    @RequestMapping("/importFace")
    @ResponseBody
    public JSONResult<String> importFaceInfo(){
        return null;
    }

    @RequestMapping("/faceSign")
    @ResponseBody
    public JSONResult<Map<String, String>> faceSign(){
        return null;
    }
}
