package com.postdesign.detectsystem.service.serviceImpl.algorithmImpl;

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
    public JSONResult<byte[]> enhanceImage(byte[] img, String uname) throws IOException, ModelNotFoundException, MalformedModelException, TranslateException {
        Path modelpath = Paths.get("src/main/trained_models/superResolution/");
        ByteArrayInputStream bis = new ByteArrayInputStream(img);
        Image image = ImageFactory.getInstance().fromInputStream(bis);
        bis.close();
        Criteria<Image, Image> criteria = Criteria.builder()
                .setTypes(Image.class, Image.class)
                .optModelPath(modelpath)
                .optModelName("RRDB_ESRGAN_x4.pt")
                .optOption("mapLocation", "true")
                .optTranslator(new superResolutionTranslator())
                .optProgress(new ProgressBar())
                .build();

        System.out.println("loadModel");
        try (ZooModel<Image, Image> zooModel = criteria.loadModel()){
            System.out.println("Finish");
            try (Predictor<Image, Image> predictor = zooModel.newPredictor()){
                System.out.println("Predict");
                Image supImage = predictor.predict(image);
                System.out.println("Finish");
                return saveImageAndReturn(supImage, uname);
            } catch (TranslateException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private JSONResult<byte[]> saveImageAndReturn(Image image, String uname) throws IOException {
        byte[] bytes;
        ByteArrayOutputStream baos = null;
        try {
            Path output = Paths.get("src/main/saveimg/superResolutionImg", uname);
            if (Files.notExists(output)){
                Files.createDirectory(output);
            }
            Path resolve = output.resolve(output.getNameCount() + ".png");
            image.save(Files.newOutputStream(resolve), "png");
            baos = new ByteArrayOutputStream();
            image.save(baos, "jpg");
            bytes = baos.toByteArray();
        }finally {
            if (baos != null){
                baos.close();
            }
        }
        return new JSONResult<>(bytes);
    }
}
