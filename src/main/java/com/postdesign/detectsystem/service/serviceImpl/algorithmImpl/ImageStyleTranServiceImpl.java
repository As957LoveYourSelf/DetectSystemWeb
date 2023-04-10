package com.postdesign.detectsystem.service.serviceImpl.algorithmImpl;

import ai.djl.Device;
import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;
import com.postdesign.detectsystem.service.algorithmService.ImageStyleTranService;
import com.postdesign.detectsystem.translators.imageStyleTranslator;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageStyleTranServiceImpl implements ImageStyleTranService {

    @Override
    // 0: people 1: scenery 2:paprika
    public byte[] animalStyle(byte[] input, Integer type, String uname) throws ModelNotFoundException, MalformedModelException, IOException {
        if (type == 0){
            return transform(input, uname, "face_paint_512_v2.pt");
        }
        if (type == 1){
            return transform(input, uname, "celeba_distill.pt");
        }
        if (type == 2){
            return transform(input, uname, "paprika.pt");
        }
        return null;
    }

    @Override
    public byte[] anyStyle(byte[] context, byte[] style, String uname) {
        return null;
    }

    private byte[] saveImageAndReturn(Image image, String uname) throws IOException {
        byte[] bytes;
        ByteArrayOutputStream baos = null;
        try {
            Path output = Paths.get("src/main/saveimg/styleTransform", uname);
            if (Files.notExists(output)){
                Files.createDirectory(output);
            }
            Path resolve = output.resolve(UUID.randomUUID() + ".png");
            image.save(Files.newOutputStream(resolve), "png");
            baos = new ByteArrayOutputStream();
            image.save(baos, "jpg");
            bytes = baos.toByteArray();
        }finally {
            if (baos != null){
                baos.close();
            }
        }
        return bytes;
    }

    private byte[] transform(byte[] input, String uname, String modelname) throws IOException, ModelNotFoundException, MalformedModelException {
        Path modelpath = Paths.get("src/main/trained_models/styleTransform/");
        ByteArrayInputStream bis = new ByteArrayInputStream(input);
        Image image = ImageFactory.getInstance().fromInputStream(bis);
        bis.close();
        Criteria<Image, Image> criteria = Criteria.builder()
                .setTypes(Image.class, Image.class)
                .optModelPath(modelpath)
                .optModelName(modelname)
                .optOption("mapLocation", "true")
                .optTranslator(new imageStyleTranslator())
                .optProgress(new ProgressBar())
                .build();

        System.out.println("loading model...");
        try (ZooModel<Image, Image> zooModel = criteria.loadModel()){
            System.out.println("Finish");
            try (Predictor<Image, Image> predictor = zooModel.newPredictor()){
                System.out.println("Predict");
                Image result = predictor.predict(image);
                System.out.println("Finish");
                return saveImageAndReturn(result, uname);
            } catch (TranslateException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private byte[] anyStyleTransform(byte[] context, byte[] style, String uname){
        return null;
    }
}
