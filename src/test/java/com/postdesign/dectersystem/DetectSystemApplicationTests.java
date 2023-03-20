package com.postdesign.dectersystem;

import ai.djl.Application;
import ai.djl.Device;
import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;
import com.postdesign.detectsystem.translators.CosFaceTranslator;
import com.postdesign.detectsystem.translators.superResolutionTranslator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest(classes = DetectSystemApplicationTests.class)
class DetectSystemApplicationTests {
    @Test
    void contextLoads() throws ModelNotFoundException, MalformedModelException, IOException {
        Path modelpath = Paths.get("src/main/trained_models/cosface");
        Path imgpath = Paths.get("src/main/saveimg/admin/微信图片_20221028170941.jpg");
        Image image = ImageFactory.getInstance().fromFile(imgpath);
        
        Criteria<Image, NDArray> criteria = Criteria.builder()
                .setTypes(Image.class, NDArray.class)
                .optModelPath(modelpath)
                .optModelName("cosface_cuda.pth")
                .optOption("mapLocation", "true")
                .optTranslator(new CosFaceTranslator())
                .optProgress(new ProgressBar())
                .build();

        try (ZooModel<Image, NDArray> zooModel = criteria.loadModel()){
            try (Predictor<Image, NDArray> predictor = zooModel.newPredictor()){
                NDArray predict = predictor.predict(image);
                NDArray distance = predict.dot(predict.transpose()).div(((predict.norm().mul(predict.norm())).add(1e-5)));
//                System.out.println(distance);
//                NDManager.subManagerOf(predict).close();
//                distance = predict.dot(predict.transpose()).div(((predict.norm().mul(predict.norm())).add(1e-5)));
                predict.close();
                double[] doubles = distance.toDoubleArray();
                System.out.println(doubles[0]);
            } catch (TranslateException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
