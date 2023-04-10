package com.postdesign.detectsystem.translators;

import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.modality.cv.util.NDImageUtils;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDArrays;
import ai.djl.ndarray.NDList;

import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.index.NDIndex;
import ai.djl.ndarray.types.Shape;
import ai.djl.opencv.OpenCVImageFactory;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.nio.file.Path;


public class ColorizationTranslator implements Translator<Image, Image> {

    private final Path path;
    private final int[] imgSize = new int[2];


    public ColorizationTranslator(Path org_imgpath,int w,int h){
        this.path = org_imgpath;
        imgSize[0] = w;
        imgSize[1] = h;
    }


    @Override
    public Image processOutput(TranslatorContext translatorContext, NDList ndList) throws Exception {
        NDArray ndArray = ndList.get(0);
        ndArray = ndArray.transpose(1,2,0);
        System.out.println(ndArray.getShape());
        ndArray = NDImageUtils.resize(ndArray, imgSize[0], imgSize[1], Image.Interpolation.BILINEAR);
        System.out.println(ndArray.getShape());
        ndArray = ndArray.transpose(2,0,1);
        System.out.println(ndArray.getShape());

        Mat grayimg = Imgcodecs.imread(path.toString());
        Image image = OpenCVImageFactory.getInstance().fromImage(grayimg);
        NDArray ndArray1 = image.toNDArray(translatorContext.getNDManager());
        ndArray1 = ndArray1.get(new NDIndex("...,2"));
        System.out.println(ndArray1.getShape());
//        System.exit(0);
        ndArray1 = ndArray1.reshape(image.getWidth(), image.getHeight(),1);
        ndArray1 = NDImageUtils.toTensor(ndArray1).transpose(0,2,1);
        System.out.println(ndArray1.getShape());

//        System.exit(0);
        NDArray concat = ndArray1.concat(ndArray, 0);
        System.out.println(concat.getShape());
//        concat = concat.clip(0,1);
//        concat = concat.mul(255);
//        System.exit(0);
        return ImageFactory.getInstance().fromNDArray(concat);
    }

    @Override
    public NDList processInput(TranslatorContext translatorContext, Image image) throws Exception {
        NDArray ndArray = image.toNDArray(translatorContext.getNDManager());
        ndArray = NDImageUtils.resize(ndArray, 256, 256, Image.Interpolation.BILINEAR);
        System.out.println(ndArray.getShape());

        ndArray = ndArray.get(new NDIndex("...,2"));
        ndArray = ndArray.reshape(256, 256,1);
        ndArray = NDImageUtils.toTensor(ndArray);
        System.out.println(ndArray.getShape());
//        System.exit(0);
        return new NDList(ndArray);
    }

}
