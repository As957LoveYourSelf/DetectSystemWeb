package com.postdesign.detectsystem.service.algorithmService;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;

import java.awt.*;
import java.io.IOException;

public interface ImageStyleTranService {
    byte[] animalStyle(byte[] input, Integer type, String uname) throws ModelNotFoundException, MalformedModelException, IOException;
    byte[] anyStyle(byte[] context, byte[] style, String uname);
}
