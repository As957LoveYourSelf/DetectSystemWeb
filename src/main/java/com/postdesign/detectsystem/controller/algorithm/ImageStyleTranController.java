package com.postdesign.detectsystem.controller.algorithm;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import com.postdesign.detectsystem.service.algorithmService.ImageStyleTranService;
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
public class ImageStyleTranController {

    @Autowired
    ImageStyleTranService imageStyleTranService;

    @RequestMapping(value = "/defaultStyleTran")
    @ResponseBody
    public JSONResult<byte[]> defaultStyle(@RequestBody Map<String, Object> map) throws IOException, ModelNotFoundException, MalformedModelException {
        byte[] img = Base64.getDecoder().decode((String)map.get("img"));
        String uname = (String) map.get("uname");
        Integer type = (Integer) map.get("type");
        byte[] result = imageStyleTranService.animalStyle(img, type, uname);
        return new JSONResult<>(result);
    }

    @RequestMapping(value = "/anyStyleTran")
    @ResponseBody
    public JSONResult<byte[]> anyStyle(@RequestBody Map<String, Object> map) throws IOException{
        return null;
    }

}
