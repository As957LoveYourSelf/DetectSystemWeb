package com.postdesign.detectsystem.controller.algorithm;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import com.postdesign.detectsystem.service.algorithmService.FaceSignSystemService;
import com.postdesign.detectsystem.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Controller
public class FaceSignSystemController {

    @Autowired
    FaceSignSystemService faceSignSystemService;

    @RequestMapping("/importFace")
    @ResponseBody
    public JSONResult<String> importFaceInfo(@RequestBody Map<String, Object> request) throws ModelNotFoundException, MalformedModelException, IOException {
        String uid = (String) request.get("uid");
        byte[] face = Base64.getDecoder().decode((String)request.get("face"));
        String s = faceSignSystemService.importFace(uid, face);
        return new JSONResult<>(s);
    }

    @RequestMapping("/faceSign")
    @ResponseBody
    public JSONResult<Map<String, Object>> faceSign(@RequestBody Map<String, Object> request) throws ModelNotFoundException, MalformedModelException, IOException {
        String classname = (String) request.get("classname");
        String course = (String) request.get("course");
        String tid = (String) request.get("tid");
        byte[] face = Base64.getDecoder().decode((String)request.get("face"));
        Map<String, Object> response = faceSignSystemService.signFace(classname, course,tid,face);
        return new JSONResult<>(response);
    }

    @RequestMapping("/getSignClasses")
    @ResponseBody
    public JSONResult<Map<String, Object>> getSignClasses(String uid){
        Map<String, Object> signClasses = faceSignSystemService.getSignClasses(uid);
        return new JSONResult<>(signClasses);
    }

    @RequestMapping("/getSignDetail")
    @ResponseBody
    public JSONResult<Map<String, Byte>> getSignDetail(String classname){
        Map<String, Byte> signDetail = faceSignSystemService.getSignDetail(classname);
        return new JSONResult<>(signDetail);
    }
}
