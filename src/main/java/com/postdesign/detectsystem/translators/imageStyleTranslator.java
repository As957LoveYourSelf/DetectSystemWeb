package com.postdesign.detectsystem.translators;

import ai.djl.ndarray.NDList;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.DataType;

public class imageStyleTranslator implements Translator<Image, Image> {

    @Override
    public Image processOutput(TranslatorContext translatorContext, NDList ndList) throws Exception {
        NDArray output = ndList.get(0).clip(-1, 1).mul(0.5).add(0.5);
        output = output.mul(255.0);
        return ImageFactory.getInstance().fromNDArray(output.squeeze(0));
    }

    @Override
    public NDList processInput(TranslatorContext translatorContext, Image image) throws Exception {
        NDManager manager = translatorContext.getNDManager();
        NDArray ndArrayImg = image.toNDArray(manager).toType(DataType.FLOAT32, false);
        ndArrayImg = ndArrayImg.mul(1.0).divi(255).mul(2.0).add(-1).transpose(2,0,1);
        System.out.println(ndArrayImg.getShape());
        return new NDList(ndArrayImg);
    }
}
