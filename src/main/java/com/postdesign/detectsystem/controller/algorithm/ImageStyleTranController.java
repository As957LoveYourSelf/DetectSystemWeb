package com.postdesign.detectsystem.controller.algorithm;

import com.postdesign.detectsystem.service.algorithmService.ImageStyleTranService;
import com.postdesign.detectsystem.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ImageStyleTranController {

    @Autowired
    ImageStyleTranService imageStyleTranService;

    @RequestMapping(value = "defaultStyleTran")
    @ResponseBody
    public JSONResult<byte[]> defaultStyle(@RequestParam("image") MultipartFile file,@RequestParam("type") Integer type) throws IOException {
        byte[] bytes = file.getBytes();
        return null;
    }


    @RequestMapping(value = "anyStyleTran")
    @ResponseBody
    public JSONResult<byte[]> anyStyle(@RequestParam("context") MultipartFile context,@RequestParam("style") MultipartFile style) throws IOException{
        byte[] contextBytes = context.getBytes();
        byte[] styleBytes = style.getBytes();
        return null;
    }

}
