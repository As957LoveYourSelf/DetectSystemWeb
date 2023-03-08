package com.postdesign.detectsystem.controller.algorithm;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import com.postdesign.detectsystem.service.algorithmService.SuperResolutionService;
import com.postdesign.detectsystem.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;

@Controller
public class SuperResolutionController {
    @Autowired
    SuperResolutionService superResolutionService;

    @RequestMapping(value = "/enhance")
    @ResponseBody
    public JSONResult<byte[]> enhance(@RequestBody Map<String, Object> map) throws TranslateException, ModelNotFoundException, MalformedModelException {
        try {
            String uname = (String) map.get("uname");
            byte[] img = Base64.getDecoder().decode((String) map.get("img"));
            if (uname != null){
                // App用户图片存储
                String root_savepath = "src/main/saveimg";
                Path path = Paths.get(root_savepath, uname);
                if (Files.notExists(path)){
                    Files.createDirectory(path);
                }
            }
            return superResolutionService.enhanceImage(img, uname);
        }catch (IOException e){
            return new JSONResult<>(401, "IOError", null);
        }
    }
}
