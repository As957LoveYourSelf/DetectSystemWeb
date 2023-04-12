package com.postdesign.detectsystem.service.algorithmService;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.postdesign.detectsystem.entity.SignDetail;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FaceSignSystemService{
    String importFace(String uid, byte[] face) throws ModelNotFoundException, MalformedModelException, IOException;
    Map<String, Object> signFace(String classname,String course,String tid, byte[] face) throws IOException, ModelNotFoundException, MalformedModelException;
    Map<String, Object> getSignClasses(String tid);
    void setFacesToRedis(String classname);
    Map<String, Byte> getSignDetail(String classname);
    String setSign(String uid, byte state);
    String endSign(String classname);
}
