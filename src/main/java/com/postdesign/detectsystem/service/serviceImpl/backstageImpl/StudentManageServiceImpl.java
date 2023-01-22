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
    public Map<String, Object> selectBySno(String sno) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sno", sno);
        Student student = studentMapper.selectOne(queryWrapper);
        return putInfoMap(student);
    }

    /**
     * 学生姓名选择器内容获取
     * */
    @Override
    public List<Map<String, Object>> selectBySname(String sname) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT sname", sname);
        List<Student> students = studentMapper.selectList(queryWrapper);
        List<Map<String, Object>> infoList = new ArrayList<>();
        if (!students.isEmpty()){
            for (Student student: students){
                infoList.add(putInfoMap(student));
            }
            return infoList;
        }
        return null;
    }


    /**
     * 通过学号和姓名搜索学生信息（可能搜索成功，或者结果唯一）
     * */
    @Override
    public Map<String, Object> selectBySNoAndSName(String sno, String sname) {
        Student student = new Student();
        student.setSno(sno);
        student.setSname(sname);
        Student stu = studentMapper.selectByMultiId(student);
        return putInfoMap(stu);
    }

    /**
     * 包装单个学生信息
     * */
    private Map<String, Object> putInfoMap(Student student) {
        if (student != null){
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("sno", student.getSno());
            objectMap.put("sname", student.getSname());
            return objectMap;
        }
        return null;
    }
}
