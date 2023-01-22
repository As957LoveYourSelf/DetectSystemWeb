package com.postdesign.detectsystem.service.serviceImpl.backstageImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.postdesign.detectsystem.entity.Student;
import com.postdesign.detectsystem.mapper.StudentMapper;
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
    /**
     * 学号选择器内容获取
     * */
    @Override
    public List<Map<String, Object>> selectBySno(String sno) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sno", sno);
        return putInfoMap(queryWrapper);
    }

    /**
     * 学生姓名选择器内容获取
     * */
    @Override
    public List<Map<String, Object>> selectBySname(String sname) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sname", sname);
        return putInfoMap(queryWrapper);
    }


    /**
     * 通过学号和姓名搜索学生信息（可能搜索成功，或者结果唯一）
     * */
    @Override
    public List<Map<String, Object>> selectBySNoAndSName(String sno, String sname) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sno", sno).eq("sname", sname);
        return putInfoMap(queryWrapper);
    }

    /**
     * 包装学生信息
     *  <el-table-column prop="class" label="所属班级" />
     *  <el-table-column prop="name" label="姓名"/>
     *  <el-table-column prop="sno" label="学号"/>
     *  <el-table-column prop="major" label="专业"/>
     *  <el-table-column prop="collage" label="学院" />
     * */
    private List<Map<String, Object>> putInfoMap(QueryWrapper<Student> queryWrapper) {
        List<Student> students = studentMapper.selectList(queryWrapper);
        List<Map<String, Object>> infoList = new ArrayList<>();
        if (!students.isEmpty()){
            for (Student student: students){
                Map<String, Object> msg = new HashMap<>();
                msg.put("class", student.getCls());
                msg.put("sname", student.getSname());
                msg.put("sno", student.getSno());
                msg.put("major", student.getMajor());
                msg.put("collage", student.getCollage());
                infoList.add(msg);
            }
            return infoList;
        }
        return null;
    }
}
