package com.postdesign.detectsystem.service.serviceImpl.backstageImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.postdesign.detectsystem.entity.*;
import com.postdesign.detectsystem.mapper.*;
import com.postdesign.detectsystem.service.backstageService.TeacherManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherManageServiceImpl implements TeacherManageService {

    @Autowired(required = false)
    CollageMapper collageMapper;
    @Autowired(required = false)
    TeacherMapper teacherMapper;
    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired(required = false)
    TeachClsMapper teachClsMapper;

    @Autowired(required = false)
    TeachMajorCourseMapper teachMajorCourseMapper;

    @Autowired(required = false)
    MajorCourseMapper majorCourseMapper;
    /**
     * 获取教师管理页面教师所属学院选择器内容
     * */
    @Override
    public List<String> getCollageSelector() {
        QueryWrapper<Collage> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name");
        List<Collage> collages = collageMapper.selectList(queryWrapper);
        List<String> collageName = new ArrayList<>();
        for (Collage c: collages){
            collageName.add(c.getName());
        }
        return collageName;
    }

    /**
     * 通过学院、学号、姓名来查询信息
     * */
    @Override
    public List<Map<String, Object>> select(String tno, String tname, String collage) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if (tno.equals("") && tname.equals("") && collage.equals("")){
            return getInfoByQueryWrapper(null);
        }
        else if (!tno.equals("") && tname.equals("") && collage.equals("")) {
            queryWrapper.eq("tno", tno);
            return getInfoByQueryWrapper(queryWrapper);
        } else if (tno.equals("") && !tname.equals("") && collage.equals("")) {
            queryWrapper.eq("tname", tname);
            return getInfoByQueryWrapper(queryWrapper);
        } else if (tno.equals("") && tname.equals("")) {
            queryWrapper.eq("collage", collage);
            return getInfoByQueryWrapper(queryWrapper);
        }else if (!tno.equals("") && !tname.equals("") && collage.equals("")){
            queryWrapper.eq("tno", tno).eq("tname", tname);
            return getInfoByQueryWrapper(queryWrapper);
        } else if (!tno.equals("") && tname.equals("")) {
            queryWrapper.eq("collage", collage).eq("tno", tno);
            return getInfoByQueryWrapper(queryWrapper);
        } else if (tno.equals("")) {
            queryWrapper.eq("collage", collage).eq("tname", tname);
            return getInfoByQueryWrapper(queryWrapper);
        }else {
            queryWrapper.eq("collage", collage).eq("tno", tno).eq("tname", tname);
            return getInfoByQueryWrapper(queryWrapper);
        }
    }

    /**
     *         <el-descriptions-item label="教师姓名" v-model="data.name"></el-descriptions-item>
     *         <el-descriptions-item label="所属学院" v-model="data.collage"></el-descriptions-item>
     *         <el-descriptions-item label="年龄" v-model="data.age" :span="2"></el-descriptions-item>
     *         <el-descriptions-item label="教学班级" v-model="data.classes" :span="2"></el-descriptions-item>
     *         <el-descriptions-item label="教学课程" v-model="data.courses" :span="2"></el-descriptions-item>
     *         <el-descriptions-item label="邮箱" v-model="data.email" :span="2"></el-descriptions-item>
     *         <el-descriptions-item label="教工号" v-model="data.no" :span="2"></el-descriptions-item>
     *         <el-descriptions-item label="电话" v-model="data.phone" ></el-descriptions-item>
     * */
    @Override
    public Map<String, Object> getTeacherDetail(String tno) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.eq("tno", tno);
        Teacher teacher = teacherMapper.selectOne(teacherQueryWrapper);
        StringBuilder classes = new StringBuilder();
        StringBuilder courses = new StringBuilder();
        Map<String, Object> data = new HashMap<>();

        QueryWrapper<TeachMajorCourse> teachCourseQueryWrapper = new QueryWrapper<>();
        QueryWrapper<TeachCls> teachClsQueryWrapper = new QueryWrapper<>();
        teachCourseQueryWrapper.eq("tno", tno);
        teachClsQueryWrapper.eq("tno",tno);

        List<TeachMajorCourse> teachMajorCours = teachMajorCourseMapper.selectList(teachCourseQueryWrapper);
        List<TeachCls> teachCls = teachClsMapper.selectList(teachClsQueryWrapper);

        for (TeachMajorCourse tc: teachMajorCours){
            courses.append(majorCourseMapper.selectById(tc.getCno()).getCname());
        }
        for (TeachCls tcls:teachCls){
            classes.append(tcls.getClassname());
        }

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("uno", tno);
        User user = userMapper.selectOne(userQueryWrapper);
        data.put("name", teacher.getTname());
        data.put("collage", teacher.getCollage());
        data.put("age", user.getUage());
        data.put("classes", classes.toString());
        data.put("courses", courses.toString());
        data.put("email", user.getEmail());
        data.put("no", teacher.getTno());
        data.put("phone", user.getUphone());
        return data;
    }

    /**
     *         <el-table-column prop="no" label="教工号"/>
     *         <el-table-column prop="title" label="职位" />
     *         <el-table-column prop="name" label="教师姓名"/>
     *         <el-table-column prop="collage" label="所属学院" />
     *         <el-table-column prop="TeachMajorCourseMapper" label="所教课程" />
     * */
    private List<Map<String, Object>> getInfoByQueryWrapper(QueryWrapper<Teacher> teacherQueryWrapper){

        List<Teacher> teachers = teacherMapper.selectList(teacherQueryWrapper);
        if (teachers != null && !teachers.isEmpty()){
            List<Map<String, Object>> info = new ArrayList<>();
            for (Teacher t:teachers){
                QueryWrapper<TeachMajorCourse> teachCourseQueryWrapper = new QueryWrapper<>();
                teachCourseQueryWrapper.eq("tno",t.getTno());
                List<TeachMajorCourse> teachMajorCours = teachMajorCourseMapper.selectList(teachCourseQueryWrapper);
                StringBuilder courses = new StringBuilder();
                for (TeachMajorCourse tc: teachMajorCours){
                    MajorCourse majorCourse = majorCourseMapper.selectById(tc.getCno());
                    courses.append(majorCourse.getCname()).append(" ");
                }
                Map<String, Object> msg = new HashMap<>();
                msg.put("tno", t.getTno());
                msg.put("title", t.getTitle());
                msg.put("tname", t.getTname());
                msg.put("collage", t.getCollage());
                msg.put("teachCourse", courses.toString());
                info.add(msg);
            }
            return info;
        }
        return null;
    }
}
