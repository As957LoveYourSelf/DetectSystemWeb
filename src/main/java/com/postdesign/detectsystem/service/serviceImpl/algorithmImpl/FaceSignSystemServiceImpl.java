package com.postdesign.detectsystem.service.serviceImpl.algorithmImpl;

import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.ndarray.NDArray;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.postdesign.detectsystem.entity.*;
import com.postdesign.detectsystem.mapper.*;
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
import java.util.*;

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

    @Autowired(required = false)
    TeachMajorCourseMapper teachMajorCourseMapper;

    @Autowired(required = false)
    TeachPublicCourseMapper teachPublicCourseMapper;

    @Autowired(required = false)
    MajorCourseMapper majorCourseMapper;

    @Autowired(required = false)
    PublicCourseMapper publicCourseMapper;
    @Autowired(required = false)
    ClassMapper classMapper;

    @Autowired(required = false)
    SignDetailMapper signDetailMapper;

    @Autowired(required = false)
    UserMapper userMapper;


    @Override
    public String importFace(String uid, byte[] face) {
        User user = userMapper.selectById(uid);
        if (user.getAddfacecount() < 2){
            Faces faces = new Faces();
            faces.setFaceImg(face);
            faces.setUid(uid);
            faceMapper.deleteById(uid);
            faceMapper.insert(faces);
            return "success";
        }else {
            return "fail";
        }
    }

    @Override
    public Map<String, Object> signFace(String classname, String course, String tid, byte[] face) throws IOException, ModelNotFoundException, MalformedModelException {
        // 确保返回重新识别时不会重复写入数据
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
                String bastUid = null;
                double bastDis = 0;
                NDArray predict = null;
                NDArray distance = null;
                for (Faces fb:faces){
                    logger.info("Detecting: " + fb.getUid());
                    ByteArrayInputStream i = new ByteArrayInputStream(fb.getFaceImg());
                    Image f = ImageFactory.getInstance().fromInputStream(i);
                    i.close();
                    predict = predictor.predict(f);
                    distance = detect.dot(predict.transpose()).div(((detect.norm().mul(predict.norm())).add(1e-5)));
                    double[] doubles = distance.toDoubleArray();
                    if (doubles[0] >= bastDis){
                        bastDis = doubles[0];
                        bastUid = fb.getUid();
                    }
                    logger.info(String.valueOf(doubles[0]));
                    logger.info("Bast: "+bastUid);
                }
                if (bastDis >= 0.4){
                    User user = userMapper.selectById(bastUid);
                    byte sign = user.getSign();
                    if (sign == (byte) 0){
                        SignDetail signDetail = new SignDetail();
                        signDetail.setClassname(classname);
                        signDetail.setCourse(course);
                        signDetail.setUid(bastUid);
                        Date date = new Date();
                        signDetail.setDate(date);
                        signDetail.setTid(tid);
                        signDetailMapper.insert(signDetail);
                        user.setSign((byte) 1);
                        userMapper.updateById(user);
                    }
                    predict.close();
                    detect.close();
                    predictor.close();
                    distance.close();
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
    public Map<String, Object> getSignClasses(String tid) {
        return getTeachClasses(tid);
    }

    @Override
    public void setFacesToRedis(String classname) {
        List<Faces> faceBytes = getFaceBytes(classname);
        redisService.set(classname, faceBytes);
    }

    @Override
    public Map<String, Byte> getSignDetail(String classname) {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("cls", classname);
        List<Student> students = studentMapper.selectList(studentQueryWrapper);
        Map<String, Byte> map = new HashMap<>();
        for (Student s:students){
            map.put(s.getSno()+"\t"+s.getSname(), userMapper.selectById(s.getSno()).getSign());
        }
        return map;
    }

    private Map<String, Object> getBastInfo(String uid){
        if (uid != null){
            Student student = studentMapper.selectById(uid);
            Faces faces = faceMapper.selectById(uid);
            Map<String, Object> detectinfo = new HashMap<>();
            detectinfo.put("uno", uid);
            detectinfo.put("name",student.getCls()+" "+student.getSname());
            detectinfo.put("face", faces.getFaceImg());
            return detectinfo;
        }
        return null;
    }

    private Map<String, Object> getTeachClasses(String tid){
        Map<String, Object> map = new HashMap<>();
        Map<String, List<String >> m = new HashMap<>();
        // class
        QueryWrapper<TeachCls> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tno", tid);
        List<TeachCls> teachCls = teachClsMapper.selectList(queryWrapper);
        List<String> classes = new ArrayList<>();
        for (TeachCls cls:teachCls){
            classes.add(cls.getClassname());
            Cls cls1 = classMapper.selectById(cls.getClassname());
            // course
            QueryWrapper<TeachMajorCourse> teachMajorCourseQueryWrapper = new QueryWrapper<>();
            QueryWrapper<TeachPublicCourse> teachPublicCourseQueryWrapper = new QueryWrapper<>();
            teachMajorCourseQueryWrapper.eq("tno", tid);
            teachPublicCourseQueryWrapper.eq("tno", tid);
            List<TeachMajorCourse> teachMajorCourses = teachMajorCourseMapper.selectList(teachMajorCourseQueryWrapper);
            List<TeachPublicCourse> teachPublicCourses = teachPublicCourseMapper.selectList(teachPublicCourseQueryWrapper);
            List<String> cs = new ArrayList<>();
            for (TeachMajorCourse t:teachMajorCourses){
                MajorCourse majorCourse = majorCourseMapper.selectById(t.getCno());
                if (majorCourse.getGrade().equals(cls1.getGrade()) && cls1.getMajor().equals(majorCourse.getMajor())){
                    cs.add(majorCourse.getCname());
                }
            }

            for (TeachPublicCourse t:teachPublicCourses){
                PublicCourse publicCourse = publicCourseMapper.selectById(t.getCpno());
                if (publicCourse.getGrade().equals(cls1.getGrade())){
                    cs.add(publicCourse.getCname());
                }
            }
            m.put(cls.getClassname(), cs);
        }
        map.put("cls", classes);
        map.put("cs", m);

        return map;
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

    private void resetSignState(String classname){
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("cls", classname);
        List<Student> students = studentMapper.selectList(studentQueryWrapper);
        for (Student s:students){
            User user = userMapper.selectById(s.getSno());
            user.setSign((byte) 0);
            userMapper.updateById(user);
        }
    }

}
