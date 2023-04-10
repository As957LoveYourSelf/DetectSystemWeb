package com.postdesign.detectsystem.controller.algorithm;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import com.postdesign.detectsystem.service.algorithmService.ColorizationService;
import com.postdesign.detectsystem.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Controller
public class ColorizationController {

    @Autowired
    ColorizationService colorizationService;

    @RequestMapping(value = "/colorization")
    @ResponseBody
    public JSONResult<byte[]> colorization(@RequestBody Map<String, Object> map){
        byte[] img = Base64.getDecoder().decode((String)map.get("img"));
        String uname = (String) map.get("uname");
        byte[] colorization = colorizationService.colorization(uname, img);
        return new JSONResult<>(colorization);
    }

}
