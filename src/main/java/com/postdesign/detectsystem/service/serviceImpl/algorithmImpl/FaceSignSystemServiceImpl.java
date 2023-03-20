package com.postdesign.detectsystem.service.serviceImpl.algorithmImpl;

import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDArrays;
import ai.djl.ndarray.NDManager;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.postdesign.detectsystem.configure.WebInterceptor;
import com.postdesign.detectsystem.entity.Faces;
import com.postdesign.detectsystem.entity.Student;
import com.postdesign.detectsystem.entity.TeachCls;
import com.postdesign.detectsystem.mapper.FaceMapper;
import com.postdesign.detectsystem.mapper.StudentMapper;
import com.postdesign.detectsystem.mapper.TeachClsMapper;
import com.postdesign.detectsystem.service.algorithmService.FaceSignSystemService;
import com.postdesign.detectsystem.service.currencyService.RedisService;
import com.postdesign.detectsystem.translators.CosFaceTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FaceSignSystemServiceImpl implements FaceSignSystemService {

    private static final Logger logger = LoggerFactory.getLogger(FaceSignSystemServiceImpl.class);

    @Autowired(required = false)
    TeachClsMapper teachClsMapper;

    @Autowired(required = false)
    FaceMapper faceMapper;

    @Autowired(required = false)
    RedisService redisService;

    @Autowired(required = false)
    StudentMapper studentMapper;

    @Override
    public String importFace(String uid, byte[] face) throws ModelNotFoundException, MalformedModelException, IOException {
            Path modelpath = Paths.get("src/main/trained_models/cosface");
            ByteArrayInputStream in = new ByteArrayInputStream(face);
            Image image = ImageFactory.getInstance().fromInputStream(in);
            in.close();
            Criteria<Image, NDArray> criteria = Criteria.builder()
                    .setTypes(Image.class, NDArray.class)
                    .optModelPath(modelpath)
                    .optModelName("cosface_cuda.pth")
                    .optOption("mapLocation", "true")
                    .optTranslator(new CosFaceTranslator())
                    .optProgress(new ProgressBar())
                    .build();

            try (ZooModel<Image, NDArray> zooModel = criteria.loadModel()){
                try (Predictor<Image, NDArray> predictor = zooModel.newPredictor()){
                    NDArray predict = predictor.predict(image);
                    byte[] bytes = predict.toByteArray();
                    predict.close();
                    predictor.close();
                    Faces faces = new Faces();
                    faces.setFaceImg(bytes);
                    faces.setUid(uid);
                    faceMapper.deleteById(uid);
                    faceMapper.insert(faces);
                    return "success";
                } catch (TranslateException e) {
                    e.printStackTrace();
                    return "error";
                }
            }
    }

    @Override
    public synchronized Map<String, String> signFace(String classname, byte[] face) throws IOException, ModelNotFoundException, MalformedModelException {
        if (redisService.get(classname) != null){
            redisService.update(classname);
        }else {
            setFacesToRedis(classname);
        }

        List<Faces> faces = (List<Faces>)redisService.get(classname);

        Path modelpath = Paths.get("src/main/trained_models/cosface");
        ByteArrayInputStream in = new ByteArrayInputStream(face);
        Image image = ImageFactory.getInstance().fromInputStream(in);
        in.close();
        Criteria<Image, NDArray> criteria = Criteria.builder()
                .setTypes(Image.class, NDArray.class)
                .optModelPath(modelpath)
                .optModelName("cosface_cuda.pth")
                .optOption("mapLocation", "true")
                .optTranslator(new CosFaceTranslator())
                .optProgress(new ProgressBar())
                .build();

        try (ZooModel<Image, NDArray> zooModel = criteria.loadModel()){
            try (Predictor<Image, NDArray> predictor = zooModel.newPredictor()){
                NDArray detect = predictor.predict(image);
                logger.info(detect.toDebugString());
                String bastUid = null;
                double bastDis = 0;
                for (Faces fb:faces){
//                    ByteArrayInputStream i = new ByteArrayInputStream(fb.getFaceImg());
                    //TODO:解决数据类型匹配问题
                    NDArray predict = NDArray.decode(NDManager.newBaseManager(), fb.getFaceImg());
                    logger.info(predict.toDebugString());
//                    System.out.println(predict);
//                    i.close();
                    NDArray distance = detect.dot(predict.transpose()).div(((detect.norm().mul(predict.norm())).add(1e-5)));
                    predict.close();
                    detect.close();
                    distance.close();
                    predictor.close();
                    double[] doubles = distance.toDoubleArray();
                    if (doubles[0] >= bastDis){
                        bastDis = doubles[0];
                        bastUid = fb.getUid();
                    }
                }
                if (bastDis >= 0.5){
                    return getBastInfo(bastUid);
                }else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public List<String> getSignClasses(String tid) {
        return getTeachClasses(tid);
    }

    @Override
    public void setFacesToRedis(String classname) {
        List<Faces> faceBytes = getFaceBytes(classname);
        redisService.set(classname, faceBytes);
    }

    private Map<String, String> getBastInfo(String uid){
        if (uid != null){
            Student student = studentMapper.selectById(uid);
            Map<String, String> detectinfo = new HashMap<>();
            detectinfo.put("uno", uid);
            detectinfo.put("name",student.getCls()+student.getSname());
            return detectinfo;
        }
        return null;
    }

    private List<String> getTeachClasses(String tid){
        QueryWrapper<TeachCls> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tno", tid);
        List<TeachCls> teachCls = teachClsMapper.selectList(queryWrapper);
        List<String> classes = new ArrayList<>();
        for (TeachCls cls:teachCls){
            classes.add(cls.getClassname());
        }
        return classes;
    }

    private List<Faces> getFaceBytes(String classname){
        List<Faces> facesinfo = new ArrayList<>();
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("cls", classname);
        List<Student> students = studentMapper.selectList(studentQueryWrapper);
        for (Student s:students){
            Faces faces = faceMapper.selectById(s.getSno());
            if (faces != null){
                facesinfo.add(faces);
            }
        }
        return facesinfo;
    }


}
