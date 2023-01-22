package com.postdesign.detectsystem.service.algorithmService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.postdesign.detectsystem.entity.Faces;

import java.util.Map;

/**
 * 业务：
 * 1. 调用指定摄像头（视频/图像）数据
 * 2. 针对数据进行目标检测
 * 3. 返回所需的检测数据：
 *  （1）身份
 *  （2）
 * 4. 后期可以通过添加摄像头数据统计课室人数
 */

public interface ObjectDetectionService{
    public Map<String, Object> faceIdentify(byte[] imageByte);

}
