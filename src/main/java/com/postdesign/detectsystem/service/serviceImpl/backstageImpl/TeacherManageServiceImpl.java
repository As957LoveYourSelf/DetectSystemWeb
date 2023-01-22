package com.postdesign.detectsystem.service.serviceImpl.backstageImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.postdesign.detectsystem.entity.Collage;
import com.postdesign.detectsystem.entity.Teacher;
import com.postdesign.detectsystem.mapper.CollageMapper;
import com.postdesign.detectsystem.mapper.TeacherMapper;
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
     * 通过学院来查询信息
     * */
    @Override
    public List<Map<String, Object>> selectByCollage(String collage) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("collage", collage);
        return getInfoByQueryWrapper(queryWrapper);
    }

    /**
     * 通过学号来查询信息
     * */
    @Override
    public List<Map<String, Object>> selectByTno(String tno) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tno", tno);
        return getInfoByQueryWrapper(queryWrapper);
    }
    /**
     * 通过学姓名查询信息
     * */
    @Override
    public List<Map<String, Object>> selectByTname(String tname) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tname", tname);
        return getInfoByQueryWrapper(queryWrapper);
    }

    /**
     * 通过学院、学号来查询信息
     * */
    @Override
    public List<Map<String, Object>> selectByCollageAndTno(String collage, String tno) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("collage", collage).eq("tno", tno);
        return getInfoByQueryWrapper(queryWrapper);
    }

    /**
     * 通过学院、姓名来查询信息
     * */
    @Override
    public List<Map<String, Object>> selectByCollageAndTname(String collage, String tname) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("collage", collage).eq("tname", tname);
        return getInfoByQueryWrapper(queryWrapper);
    }

    /**
     * 通过学号、姓名来查询信息
     * */
    @Override
    public List<Map<String, Object>> selectByTnoAndTName(String tno, String tname) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tno", tno).eq("tname", tname);
        return getInfoByQueryWrapper(queryWrapper);
    }

    /**
     * 通过学院、学号、姓名来查询信息
     * */
    @Override
    public List<Map<String, Object>> selectByTnoAndTNameAndCollage(String tno, String tname, String collage) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("collage", collage).eq("tno", tno).eq("tname", tname);
        return getInfoByQueryWrapper(queryWrapper);
    }


    /**
     *         <el-table-column prop="no" label="教工号"/>
     *         <el-table-column prop="title" label="职位" />
     *         <el-table-column prop="name" label="教师姓名"/>
     *         <el-table-column prop="collage" label="所属学院" />
     *         <el-table-column prop="teachCourse" label="所教课程" />
     * */
    private List<Map<String, Object>> getInfoByQueryWrapper(QueryWrapper<Teacher> queryWrapper){
        List<Teacher> teachers = teacherMapper.selectList(queryWrapper);
        if (!teachers.isEmpty()){
            List<Map<String, Object>> info = new ArrayList<>();
            for (Teacher t:teachers){
                Map<String, Object> msg = new HashMap<>();
                msg.put("tno", t.getTno());
                msg.put("title", t.getTitle());
                msg.put("tname", t.getTname());
                msg.put("collage", t.getCollage());
                msg.put("teachCourse", t.getTeachCourse());
                info.add(msg);
            }
            return info;
        }
        return null;
    }
}
