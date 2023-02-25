package com.postdesign.detectsystem.service.serviceImpl.backstageImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.postdesign.detectsystem.entity.*;
import com.postdesign.detectsystem.mapper.*;
import com.postdesign.detectsystem.service.backstageService.ClassMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassMangerServiceImpl implements ClassMangerService {

    @Autowired(required = false)
    GradeMapper gradeMapper;
    @Autowired(required = false)
    CollegeMapper collegeMapper;
    @Autowired(required = false)
    ClassMapper classMapper;
    @Autowired(required = false)
    TeacherMapper teacherMapper;
    @Autowired(required = false)
    StudentMapper studentMapper;

    /**
     * 获取年级选择器内容
     * */
    @Override
    public List<Integer> getGradeSelector() {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("grade");
        List<Grade> gradeList = gradeMapper.selectList(queryWrapper);
        List<Integer> gradeSelector = new ArrayList<>();
        if (!gradeList.isEmpty()){
            for (Grade g:gradeList){
                gradeSelector.add(g.getGrade());
            }
            return gradeSelector;
        }
        return null;
    }

    /**
     * 获取学院选择器内容
     **/
    @Override
    public List<String> getCollegeSelector() {
        QueryWrapper<College> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name");
        List<College> collegeList = collegeMapper.selectList(queryWrapper);
        List<String> collegeSelector = new ArrayList<>();
        if (!collegeList.isEmpty()){
            for (College c: collegeList){
                collegeSelector.add(c.getName());
            }
            return collegeSelector;
        }
        return null;
    }
    /**
     * 查找对应班级
     **/
    @Override
    public List<Map<String, Object>> select(Integer grade, String college) {
        if (college == null){
            return null;
        }

        QueryWrapper<Cls> queryWrapper = new QueryWrapper<>();
        if (college.equals("") && grade != null){
            queryWrapper.eq("grade", grade);
            return getClassInfoByQueryWrapper(queryWrapper);
        }else if (!college.equals("") && grade == null){
            queryWrapper.eq("college", college);
            return getClassInfoByQueryWrapper(queryWrapper);
        }else if (!college.equals("")){
            queryWrapper.eq("grade", grade).eq("college", college);
            return getClassInfoByQueryWrapper(queryWrapper);
        }else {
            return getClassInfoByQueryWrapper(null);
        }
    }

    /**
     *         班级信息
     *         <el-descriptions-item label="班级名称" v-model="data.name"></el-descriptions-item>
     *         <el-descriptions-item label="所属学院" v-model="data.college"></el-descriptions-item>
     *         <el-descriptions-item label="所属专业" v-model="data.major"></el-descriptions-item>
     *         <el-descriptions-item label="班级人数" v-model="data.peoplenum" ></el-descriptions-item>
     *         <el-descriptions-item label="班主任" v-model="data.headmaster"></el-descriptions-item>
     *         班级成员
     *         data.people
     *         <el-table-column prop="class" label="所属班级" />
     *         <el-table-column prop="name" label="姓名"/>
     *         <el-table-column prop="sno" label="学号"/>
     *         <el-table-column prop="major" label="专业"/>
     *         <el-table-column prop="college" label="学院" />
     * */
    @Override
    public Map<String, Object> getClassDetail(String clsName) {
        QueryWrapper<Cls> clsQueryWrapper = new QueryWrapper<>();
        clsQueryWrapper.eq("className", clsName);
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.eq("leadClass", clsName);
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("cls", clsName);

        Cls cls = classMapper.selectOne(clsQueryWrapper);
        Teacher teacher = teacherMapper.selectOne(teacherQueryWrapper);
        List<Student> students = studentMapper.selectList(studentQueryWrapper);

        if (cls != null && teacher != null && students != null){
            Map<String, Object> data = new HashMap<>();
            data.put("name", cls.getClassName());
            data.put("college", cls.getCollege());
            data.put("major", cls.getMajor());
            data.put("headmaster", teacher.getTname());
            List<Map<String, Object>> peopledata = new ArrayList<>();
            for(Student s:students){
                Map<String, Object> info = new HashMap<>();
                info.put("class", s.getCls());
                info.put("name", s.getSname());
                info.put("sno", s.getSno());
                info.put("major",s.getMajor());
                info.put("college",s.getCollege());
                peopledata.add(info);
            }
            data.put("people", peopledata);
            return data;
        }
        return null;
    }

    private List<Map<String, Object>> getClassInfoByQueryWrapper(QueryWrapper<Cls> clumnQueryWrapper) {

        List<Cls> clsList = classMapper.selectList(clumnQueryWrapper);
        if (!clsList.isEmpty()){
            List<Map<String, Object>> infoList = new ArrayList<>();
            for (Cls c:clsList){
                QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("leadclass",c.getClassName());
                Teacher teacher = teacherMapper.selectOne(queryWrapper);
                Map<String, Object> objectMap = new HashMap<>();

                objectMap.put("class_name", c.getClassName());
                objectMap.put("headmaster", teacher.getTname());
                objectMap.put("major", c.getMajor());
                objectMap.put("college", c.getCollege());
                objectMap.put("grade", c.getGrade());
                infoList.add(objectMap);
            }
            return infoList;
        }
        return null;
    }

}
