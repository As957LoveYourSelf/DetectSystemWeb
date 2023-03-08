package com.postdesign.detectsystem.service.serviceImpl.backstageImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.postdesign.detectsystem.entity.Student;
import com.postdesign.detectsystem.entity.User;
import com.postdesign.detectsystem.mapper.StudentMapper;
import com.postdesign.detectsystem.mapper.UserMapper;
import com.postdesign.detectsystem.service.backstageService.StudentManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentManageServiceImpl implements StudentManageService {
    @Autowired(required = false)
    StudentMapper studentMapper;

    @Autowired(required = false)
    UserMapper userMapper;
    /**
     * 通过学号和姓名搜索学生信息（可能搜索成功，或者结果唯一）
     * */
    @Override
    public List<Map<String, Object>> select(String sno, String sname) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if (sno == null || sname == null){
            return null;
        }
        if (sno.equals("") && sname.equals("")){
            return putInfoMap(null);
        }else if (!sno.equals("") && sname.equals("")){
            queryWrapper.like("sno", sno);
            return putInfoMap(queryWrapper);
        } else if (sno.equals("")) {
            queryWrapper.like("sname", sname);
            return putInfoMap(queryWrapper);
        }else {
            queryWrapper.like("sno", sno).like("sname", sname);
            return putInfoMap(queryWrapper);
        }
    }

    /**
     *         <el-descriptions-item label="学生姓名" v-model="data.name"></el-descriptions-item>
     *         <el-descriptions-item label="学号" v-model="data.no"></el-descriptions-item>
     *         <el-descriptions-item label="所属学院" v-model="data.collage"></el-descriptions-item>
     *         <el-descriptions-item label="专业" v-model="data.major" :span="2"></el-descriptions-item>
     *         <el-descriptions-item label="年龄" v-model="data.age" :span="2"></el-descriptions-item>
     *         <el-descriptions-item label="邮箱" v-model="data.email" :span="2"></el-descriptions-item>
     *         <el-descriptions-item label="电话" v-model="data.phone" :span="2"></el-descriptions-item>
     *         <el-descriptions-item label="年级" v-model="data.grade" :span="2"></el-descriptions-item>
     *         <el-descriptions-item label="班级" v-model="data.class" :span="2"></el-descriptions-item>
     * */
    @Override
    public Map<String, Object> getStudentDetail(String sno) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("uid", sno);
        User user = userMapper.selectOne(userQueryWrapper);
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("sno", sno);
        Student student = studentMapper.selectOne(studentQueryWrapper);
        if (user != null && student != null){
            Map<String, Object> info = new HashMap<>();
            info.put("name", user.getUname());
            info.put("no", user.getUid());
            info.put("college", student.getCollege());
            info.put("major", student.getMajor());
            info.put("age", user.getUage() == null?20:user.getUage());
            info.put("email", user.getEmail() == null?"":user.getEmail());
            info.put("phone", user.getUphone()== null?"":user.getUphone());
            info.put("grade", student.getGrade());
            info.put("class", student.getCls());
            info.put("introduce", user.getIntroduce()== null?"":user.getIntroduce());
            info.put("sex", user.getSex());
            return info;
        }
        return null;
    }

    /**
     * 包装学生信息
     *  <el-table-column prop="class" label="所属班级" />
     *  <el-table-column prop="sname" label="姓名"/>
     *  <el-table-column prop="sno" label="学号"/>
     *  <el-table-column prop="major" label="专业"/>
     *  <el-table-column prop="collage" label="学院" />
     * */
    private List<Map<String, Object>> putInfoMap(QueryWrapper<Student> queryWrapper) {
        List<Student> students = studentMapper.selectList(queryWrapper);
        List<Map<String, Object>> infoList = new ArrayList<>();
        if (students != null && !students.isEmpty()){
            for (Student student: students){
                Map<String, Object> msg = new HashMap<>();
                msg.put("class", student.getCls());
                msg.put("sname", student.getSname());
                msg.put("sno", student.getSno());
                msg.put("major", student.getMajor());
                msg.put("college", student.getCollege());
                infoList.add(msg);
            }
            return infoList;
        }
        return null;
    }
}
