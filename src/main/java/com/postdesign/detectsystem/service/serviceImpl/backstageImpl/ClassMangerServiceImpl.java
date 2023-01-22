package com.postdesign.detectsystem.service.serviceImpl.backstageImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.postdesign.detectsystem.entity.Cls;
import com.postdesign.detectsystem.entity.Collage;
import com.postdesign.detectsystem.entity.Grade;
import com.postdesign.detectsystem.entity.Teacher;
import com.postdesign.detectsystem.mapper.ClassMapper;
import com.postdesign.detectsystem.mapper.CollageMapper;
import com.postdesign.detectsystem.mapper.GradeMapper;
import com.postdesign.detectsystem.mapper.TeacherMapper;
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
    CollageMapper collageMapper;

    @Autowired(required = false)
    ClassMapper classMapper;

    @Autowired(required = false)
    TeacherMapper teacherMapper;

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
    public List<String> getCollageSelector() {
        QueryWrapper<Collage> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name");
        List<Collage> collageList = collageMapper.selectList(queryWrapper);
        List<String> collageSelector = new ArrayList<>();
        if (!collageList.isEmpty()){
            for (Collage c:collageList){
                collageSelector.add(c.getName());
            }
            return collageSelector;
        }
        return null;
    }

    /**
     * 通过年级查找对应班级
     **/
    @Override
    public List<Map<String, Object>> selectByGrade(Integer grade) {
        QueryWrapper<Cls> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("grade", grade);
        return getClassInfoByQueryWrapper(queryWrapper);
    }

    /**
     *
     **/
    @Override
    public List<Map<String, Object>> selectByCollage(String collage) {
        QueryWrapper<Cls> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("collage", collage);
        return getClassInfoByQueryWrapper(queryWrapper);
    }

    /**
     *
     **/
    @Override
    public List<Map<String, Object>> selectByGradeAndCollage(Integer grade, String collage) {
        QueryWrapper<Cls> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("grade",grade).eq("collage", collage);
        return getClassInfoByQueryWrapper(queryWrapper);
    }


    /**
     * 获取班级具体信息（人数、成员）
     * */
    @Override
    public List<Map<String, Object>> getClassInfo(String className) {
        return null;
    }

    private List<Map<String, Object>> getClassInfoByQueryWrapper(QueryWrapper<Cls> clumnQueryWrapper) {
        List<Cls> clsList = classMapper.selectList(clumnQueryWrapper);
        if (!clsList.isEmpty()){
            List<Map<String, Object>> infoList = new ArrayList<>();
            for (Cls c:clsList){
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("class_name", c.getClassName());
                QueryWrapper<Teacher> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("leadclass",c.getClassName());
                Teacher teacher = teacherMapper.selectOne(queryWrapper1);
                objectMap.put("headmaster", teacher.getTname());
                objectMap.put("major", c.getMajor());
                objectMap.put("collage", c.getCollage());
                objectMap.put("grade", c.getGrade());
                infoList.add(objectMap);
            }
            return infoList;
        }
        return null;
    }

}
