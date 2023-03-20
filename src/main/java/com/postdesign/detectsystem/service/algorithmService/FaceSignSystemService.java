package com.postdesign.detectsystem.service.algorithmService;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FaceSignSystemService {
    String importFace(String uid, byte[] face) throws ModelNotFoundException, MalformedModelException, IOException;
    Map<String, String> signFace(String classname, byte[] face) throws IOException, ModelNotFoundException, MalformedModelException;
    List<String> getSignClasses(String tid);
    void setFacesToRedis(String classname);
}
