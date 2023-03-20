package com.postdesign.detectsystem.translators;

import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.transform.Normalize;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.modality.cv.util.NDImageUtils;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.DataType;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;

public class CosFaceTranslator implements Translator<Image, NDArray> {



    @Override
    public NDArray processOutput(TranslatorContext translatorContext, NDList ndList) throws Exception {
//        System.out.println(ndList.get(0));
        ndList.detach();
//        NDArray predict = ndList.get(0);
//        predict = predict.dot(predict.transpose()).div(((predict.norm().mul(predict.norm())).add(1e-5)));
//        System.out.println(predict);
        return ndList.get(0);
    }

    @Override
    public NDList processInput(TranslatorContext translatorContext, Image image) throws Exception {
        NDManager manager = translatorContext.getNDManager();
        NDArray ndArrayImg = image.toNDArray(manager).toType(DataType.FLOAT32, false);
        ndArrayImg = NDImageUtils.toTensor(ndArrayImg);
//        System.out.println(ndArrayImg.getShape());
        ndArrayImg = NDImageUtils.normalize(ndArrayImg, new float[]{0.5f,0.5f,0.5f}, new float[]{0.5f,0.5f,0.5f}).transpose(1,2,0);
//        System.out.println(ndArrayImg.getShape());
//        ndArrayImg = NDImageUtils.centerCrop(ndArrayImg, 96, 112).transpose(2,0,1);
        ndArrayImg = ndArrayImg.transpose(2,0,1);
//        System.out.println(ndArrayImg.getShape());
        return new NDList(ndArrayImg);
    }
}
