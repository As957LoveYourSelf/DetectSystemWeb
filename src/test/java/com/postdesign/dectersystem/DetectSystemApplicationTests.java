package com.postdesign.dectersystem;

import ai.djl.Application;
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
        Path modelpath = Paths.get("src/main/java/com/postdesign/detectsystem/trained_models/superResolution/");
        Path imgpath = Paths.get("src/main/saveimg/comic.png");
        Image image = ImageFactory.getInstance().fromFile(imgpath);
        
        Criteria<Image, Image> criteria = Criteria.builder()
                .setTypes(Image.class, Image.class)
                .optModelPath(modelpath)
                .optModelName("RRDB_ESRGAN_x4.pt")
                .optTranslator(new superResolutionTranslator())
                .optProgress(new ProgressBar())
                .build();

        try (ZooModel<Image, Image> zooModel = criteria.loadModel()){
            try (Predictor<Image, Image> predictor = zooModel.newPredictor()){
                Image supImage = predictor.predict(image);
                Path output = Paths.get("src/main/saveimg/output");
                Path resolve = output.resolve(output.getNameCount() + ".png");
                supImage.save(Files.newOutputStream(resolve), "png");
            } catch (TranslateException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
