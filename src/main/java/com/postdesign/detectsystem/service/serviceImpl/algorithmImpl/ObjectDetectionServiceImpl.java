package com.postdesign.detectsystem.service.serviceImpl.algorithmImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.postdesign.detectsystem.entity.Faces;
import com.postdesign.detectsystem.mapper.FaceMapper;
import com.postdesign.detectsystem.service.algorithmService.ObjectDetectionService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


@Service
public class ObjectDetectionServiceImpl extends ServiceImpl<FaceMapper, Faces> implements ObjectDetectionService {


    @Override
    public Map<String, Object> faceIdentify(byte[] imageByte) {
        BufferedImage img = null;
        InputStream buffin = new ByteArrayInputStream(imageByte);
        try {
             img = ImageIO.read(buffin);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
    /**
     * 实现人脸识别算法流程
     * */
    private String yolov7Identify(){
        return null;
    }
}
