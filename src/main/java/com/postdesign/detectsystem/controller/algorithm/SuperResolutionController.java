package com.postdesign.detectsystem.controller.algorithm;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import com.postdesign.detectsystem.service.algorithmService.SuperResolutionService;
import com.postdesign.detectsystem.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class SuperResolutionController {
    @Autowired
    SuperResolutionService superResolutionService;

    @RequestMapping(value = "/enhance")
    @ResponseBody
    public JSONResult<byte[]> enhance(byte[] img) throws TranslateException, ModelNotFoundException, MalformedModelException {
        try {
            return superResolutionService.enhanceImage(img);
        }catch (IOException e){
            return new JSONResult<>(401, "IOError", null);
        }
    }

    @RequestMapping("/upload")
    @ResponseBody
    public JSONResult<String> upload(MultipartFile file, String uname){
        try {
            byte[] bytes = file.getBytes();
            String root_savepath = "src/main/saveimg";
            Path path = Paths.get(root_savepath, uname);
            if (Files.notExists(path)){
                Files.createDirectory(path);
            }
            Path save_img = Paths.get(path.toString(), file.getOriginalFilename());
            // 写入图片二进制流
            Files.write(save_img, bytes);
            return new JSONResult<>("/api/"+ save_img);
        }catch (IOException e){
            return new JSONResult<>(401,"IOError",null);
        }
    }

    @RequestMapping("/downloadImg")
    @ResponseBody
    public JSONResult<byte[]> downloadImg(String imgname){
        return null;
    }
}
