package com.postdesign.dectersystem;

import ai.djl.Application;
import ai.djl.Device;
import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.util.NDImageUtils;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDManager;
import ai.djl.opencv.OpenCVImageFactory;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;
import com.postdesign.detectsystem.translators.ColorizationTranslator;
import com.postdesign.detectsystem.utils.JSONResult;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = DetectSystemApplicationTests.class)
class DetectSystemApplicationTests {
    @Test
    void contextLoads() throws ModelNotFoundException, MalformedModelException, IOException {
        Process proc;
        try {
            String[] args1=new String[]{"python","src/main/python/colorization_release.py","-iD:\\IdeaObjects\\DetectSystemWeb\\src\\main\\saveimg\\admin\\img.png"};
            proc = Runtime.getRuntime().exec(args1);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            int i = proc.waitFor();
            System.out.println(i);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

//        Path modelpath = Paths.get("src/main/trained_models/colorization");
//        Path imgpath = Paths.get("src/main/saveimg/admin/img.png");
//
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        Mat img = Imgcodecs.imread(imgpath.toString());
//        ColorizationTranslator colorizationTranslator = new ColorizationTranslator(imgpath, img.width(), img.height());
//        Imgproc.resize(img, img, new Size(256, 256));
//        Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2Lab);
////        System.out.println(img.size());
//        Image image = OpenCVImageFactory.getInstance().fromImage(img);
//
//        Criteria<Image, Image> criteria = Criteria.builder()
//                .setTypes(Image.class, Image.class)
//                .optModelPath(modelpath)
//                .optModelName("siggraph17_cuda.pt")
//                .optOption("mapLocation", "true")
//                .optDevice(Device.cpu())
//                .optTranslator(colorizationTranslator)
//                .optProgress(new ProgressBar())
//                .build();
//
//        try (ZooModel<Image, Image> zooModel = criteria.loadModel()){
//            try (Predictor<Image, Image> predictor = zooModel.newPredictor()){
//                Image predict = predictor.predict(image);
//                System.out.println(predict.toNDArray(NDManager.newBaseManager()).getShape());
//
//                Path output = Paths.get("src/main/saveimg/colorization", "test");
//                if (Files.notExists(output)){
//                    Files.createDirectory(output);
//                }
//                Path resolve = output.resolve(UUID.randomUUID()+ ".png");
//                predict.save(Files.newOutputStream(resolve), "png");
//                System.exit(0);
//
//                Mat res = Imgcodecs.imread(resolve.toString());
//                Imgproc.cvtColor(res, res, Imgproc.COLOR_Lab2RGB);
//                Imgcodecs.imwrite(resolve.toString(), res);
//
//            } catch (TranslateException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }

}
