package com.postdesign.detectsystem.translators;

import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.DataType;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;


public class superResolutionTranslator implements Translator<Image, Image> {

    @Override
    public Image processOutput(TranslatorContext translatorContext, NDList ndList) throws Exception {
        NDArray output = ndList.get(0).clip(0, 1);
        output = output.mul(255.0);
        return ImageFactory.getInstance().fromNDArray(output.squeeze());
    }

    @Override
    public NDList processInput(TranslatorContext translatorContext, Image image) throws Exception {
        NDManager manager = translatorContext.getNDManager();
        NDArray ndArrayImg = image.toNDArray(manager).toType(DataType.FLOAT32, false);
        ndArrayImg = ndArrayImg.mul(1.0).divi(255).transpose(2,0,1);
        System.out.println(ndArrayImg.getShape());
        return new NDList(ndArrayImg);
    }
}
