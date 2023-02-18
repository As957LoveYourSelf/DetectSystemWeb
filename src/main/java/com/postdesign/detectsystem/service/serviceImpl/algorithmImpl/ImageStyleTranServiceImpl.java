package com.postdesign.detectsystem.service.serviceImpl.algorithmImpl;

import com.postdesign.detectsystem.service.algorithmService.ImageStyleTranService;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class ImageStyleTranServiceImpl implements ImageStyleTranService {

    @Override
    // 0: people 1: scenery
    public Image animalStyle(Image input, Integer type) {
        if (type == 0){

        }
        if (type == 1){

        }

        return null;
    }

    @Override
    public Image anyStyle(Image context, Image style) {
        return null;
    }


}
