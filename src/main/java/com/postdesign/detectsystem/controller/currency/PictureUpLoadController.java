package com.postdesign.detectsystem.controller.currency;

import com.postdesign.detectsystem.utils.JSONResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class PictureUpLoadController {
    @RequestMapping("/upload")
    @ResponseBody
    public JSONResult<byte[]> upload(MultipartFile file){
        try {
            byte[] bytes = file.getBytes();
            String root_savepath = "src/main/saveimg";
            Path path = Paths.get(root_savepath, "admin");
            if (Files.notExists(path)){
                Files.createDirectory(path);
            }
            Path save_img = Paths.get(path.toString(), file.getOriginalFilename());
            // 写入图片二进制流
            if (Files.notExists(save_img)){
                Files.write(save_img, bytes);
            }
            return new JSONResult<>(bytes);
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
