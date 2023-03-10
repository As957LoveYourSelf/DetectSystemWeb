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
    CollegeMapper collegeMapper;
    @Autowired(required = false)
    TeacherMapper teacherMapper;
    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired(required = false)
    TeachClsMapper teachClsMapper;

    @Autowired(required = false)
    TeachMajorCourseMapper teachMajorCourseMapper;

    @Autowired(required = false)
    TeacherPublicCourseMapper teacherPublicCourseMapper;

    @Autowired(required = false)
    MajorCourseMapper majorCourseMapper;

    @Autowired(required = false)
    PublicCourseMapper publicCourseMapper;
    /**
     * 获取教师管理页面教师所属学院选择器内容
     * */
    @Override
    public List<String> getCollegeSelector() {
        QueryWrapper<College> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name");
        List<College> colleges = collegeMapper.selectList(queryWrapper);
        List<String> collegeName = new ArrayList<>();
        for (College c: colleges){
            collegeName.add(c.getName());
        }
        return collegeName;
    }

    /**
     * 通过学院、学号、姓名来查询信息
     * */
    @Override
    public List<Map<String, Object>> select(String tno, String tname, String college) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if (tno.equals("") && tname.equals("") && college.equals("")){
            return getInfoByQueryWrapper(null);
        }
        else if (!tno.equals("") && tname.equals("") && college.equals("")) {
            queryWrapper.like("tno", tno);
            return getInfoByQueryWrapper(queryWrapper);
        } else if (tno.equals("") && !tname.equals("") && college.equals("")) {
            queryWrapper.like("tname", tname);
            return getInfoByQueryWrapper(queryWrapper);
        } else if (tno.equals("") && tname.equals("")) {
            queryWrapper.eq("college", college);
            return getInfoByQueryWrapper(queryWrapper);
        }else if (!tno.equals("") && !tname.equals("") && college.equals("")){
            queryWrapper.like("tno", tno).like("tname", tname);
            return getInfoByQueryWrapper(queryWrapper);
        } else if (!tno.equals("") && tname.equals("")) {
            queryWrapper.eq("college", college).like("tno", tno);
            return getInfoByQueryWrapper(queryWrapper);
        } else if (tno.equals("")) {
            queryWrapper.eq("college", college).like("tname", tname);
            return getInfoByQueryWrapper(queryWrapper);
        }else {
            queryWrapper.eq("college", college).like("tno", tno).like("tname", tname);
            return getInfoByQueryWrapper(queryWrapper);
        }
    }

    /**
     *         <el-descriptions-item label="教师姓名" v-model="data.name"></el-descriptions-item>
     *         <el-descriptions-item label="所属学院" v-model="data.college"></el-descriptions-item>
     *         <el-descriptions-item label="年龄" v-model="data.age" :span="2"></el-descriptions-item>
     *         <el-descriptions-item label="教学班级" v-model="data.classes" :span="2"></el-descriptions-item>
     *         <el-descriptions-item label="教学课程" v-model="data.courses" :span="2"></el-descriptions-item>
     *         <el-descriptions-item label="邮箱" v-model="data.email" :span="2"></el-descriptions-item>
     *         <el-descriptions-item label="教工号" v-model="data.no" :span="2"></el-descriptions-item>
     *         <el-descriptions-item label="电话" v-model="data.phone" ></el-descriptions-item>
     * */
    @Override
    public Map<String, Object> getTeacherDetail(String tno) {
        Teacher teacher = teacherMapper.selectById(tno);
        StringBuilder classes = new StringBuilder();
        StringBuilder courses = new StringBuilder();
        Map<String, Object> data = new HashMap<>();

        QueryWrapper<TeachMajorCourse> teachCourseQueryWrapper = new QueryWrapper<>();
        QueryWrapper<TeachPublicCourse> teachPublicCourseQueryWrapper = new QueryWrapper<>();
        QueryWrapper<TeachCls> teachClsQueryWrapper = new QueryWrapper<>();
        teachCourseQueryWrapper.eq("tno", tno);
        teachClsQueryWrapper.eq("tno",tno);
        teachPublicCourseQueryWrapper.eq("tno", tno);

        List<TeachPublicCourse> teachPublicCourses = teacherPublicCourseMapper.selectList(teachPublicCourseQueryWrapper);
        List<TeachMajorCourse> teachMajorCours = teachMajorCourseMapper.selectList(teachCourseQueryWrapper);
        List<TeachCls> teachCls = teachClsMapper.selectList(teachClsQueryWrapper);

        for (TeachMajorCourse tc: teachMajorCours){
            courses.append(majorCourseMapper.selectById(tc.getCno()).getCname()).append(" ");
        }

        for (TeachPublicCourse tc: teachPublicCourses){
            courses.append(publicCourseMapper.selectById(tc.getCpno()).getCname()).append(" ");
        }

        for (TeachCls tcls:teachCls){
            classes.append(tcls.getClassname());
        }

        User user = userMapper.selectById(tno);
        data.put("name", teacher.getTname());
        data.put("college", teacher.getCollege());
        data.put("age", user.getUage().intValue());
        data.put("classes", classes.toString());
        data.put("courses", courses.toString());
        data.put("email", user.getEmail());
        data.put("no", teacher.getTno());
        data.put("phone", user.getUphone());
        data.put("introduce", user.getIntroduce());
        data.put("sex", user.getSex());
        return data;
    }

    /**
     *         <el-table-column prop="tno" label="教工号"/>
     *         <el-table-column prop="title" label="职位" />
     *         <el-table-column prop="tname" label="教师姓名"/>
     *         <el-table-column prop="college" label="所属学院" />
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

                QueryWrapper<TeachPublicCourse> teachPublicCourseQueryWrapper = new QueryWrapper<>();
                teachPublicCourseQueryWrapper.eq("tno",t.getTno());
                List<TeachPublicCourse> teachPublicCourses = teacherPublicCourseMapper.selectList(teachPublicCourseQueryWrapper);
                for (TeachPublicCourse tc: teachPublicCourses){
                    PublicCourse publicCourse = publicCourseMapper.selectById(tc.getCpno());
                    courses.append(publicCourse.getCname()).append(" ");
                }

                Map<String, Object> msg = new HashMap<>();
                msg.put("tno", t.getTno());
                msg.put("title", t.getTitle());
                msg.put("tname", t.getTname());
                msg.put("college", t.getCollege());
                msg.put("teachCourse", courses.toString());
                info.add(msg);
            }
            return info;
        }
        return null;
    }
}
