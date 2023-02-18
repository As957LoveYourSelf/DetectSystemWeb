package com.postdesign.detectsystem.controller.algorithm;

import ai.djl.MalformedModelException;
import ai.djl.ModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import com.postdesign.detectsystem.service.algorithmService.SuperResolutionService;
import com.postdesign.detectsystem.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = "/enhance", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public JSONResult<byte[]> enhance(@RequestParam("image") MultipartFile file) throws IOException, TranslateException, ModelNotFoundException, MalformedModelException {
        byte[] bytes = file.getBytes();
        String root_savepath = "src/main/saveimg";
        Path path = Paths.get(root_savepath, file.getOriginalFilename());
        Files.write(path, bytes);
        return superResolutionService.enhanceImage(path);
    }
}
