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
        ndList.detach();
        return ndList.get(0);
    }

    @Override
    public NDList processInput(TranslatorContext translatorContext, Image image) throws Exception {
        NDManager manager = translatorContext.getNDManager();
        NDArray ndArrayImg = image.toNDArray(manager).toType(DataType.FLOAT32, false);
        ndArrayImg = NDImageUtils.toTensor(ndArrayImg);
        ndArrayImg = NDImageUtils.normalize(ndArrayImg, new float[]{0.5f,0.5f,0.5f}, new float[]{0.5f,0.5f,0.5f}).transpose(1,2,0);
        ndArrayImg = ndArrayImg.transpose(2,0,1);
        return new NDList(ndArrayImg);
    }
}
