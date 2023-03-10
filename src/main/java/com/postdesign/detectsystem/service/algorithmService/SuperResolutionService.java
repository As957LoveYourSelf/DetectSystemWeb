package com.postdesign.detectsystem.service.algorithmService;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import com.postdesign.detectsystem.utils.JSONResult;

import java.io.IOException;
import java.nio.file.Path;

public interface SuperResolutionService {
    JSONResult<byte[]> enhanceImage(byte[] image, String uname) throws IOException, ModelNotFoundException, MalformedModelException, TranslateException;
}
