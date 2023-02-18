package com.postdesign.detectsystem.service.serviceImpl.algorithmImpl;

import ai.djl.Application;
import ai.djl.Device;
import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;
import com.postdesign.detectsystem.service.algorithmService.SuperResolutionService;
import com.postdesign.detectsystem.translators.superResolutionTranslator;
import com.postdesign.detectsystem.utils.IOUtil;
import com.postdesign.detectsystem.utils.JSONResult;
import org.springframework.stereotype.Service;

import ai.djl.modality.cv.Image;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class SuperResolutionServiceImpl implements SuperResolutionService {
    @Override
    public JSONResult<byte[]> enhanceImage(Path imgpath) throws IOException, ModelNotFoundException, MalformedModelException, TranslateException {
        Path modelpath = Paths.get("src/main/java/com/postdesign/detectsystem/trained_models/superResolution/");
        Image image = ImageFactory.getInstance().fromFile(imgpath);

        Criteria<Image, Image> criteria = Criteria.builder()
                .optApplication(Application.CV.IMAGE_ENHANCEMENT)
                .setTypes(Image.class, Image.class)
                .optModelPath(modelpath)
                .optModelName("RRDB_ESRGAN_x4.pth")
                .optTranslator(new superResolutionTranslator())
                .optEngine("Pytorch")
                .optDevice(Device.gpu(0))
                .optProgress(new ProgressBar())
                .build();

        try (ZooModel<Image, Image> zooModel = criteria.loadModel()){
            try (Predictor<Image, Image> predictor = zooModel.newPredictor()){
                Image supImage = predictor.predict(image);
                return saveImageAndReturn(supImage);
            }
        }
    }

    private JSONResult<byte[]> saveImageAndReturn(Image image) throws IOException {
        Path output = Paths.get("src/main/saveimg/output");
        Path resolve = output.resolve(output.getNameCount() + ".png");
        image.save(Files.newOutputStream(resolve), "png");
        return new JSONResult<>(IOUtil.image2byte(resolve.toFile()));
    }
}
