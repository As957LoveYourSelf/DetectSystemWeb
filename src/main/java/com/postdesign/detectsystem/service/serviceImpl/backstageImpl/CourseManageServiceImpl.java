package com.postdesign.detectsystem.service.serviceImpl.backstageImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.postdesign.detectsystem.entity.*;
import com.postdesign.detectsystem.mapper.*;
import com.postdesign.detectsystem.service.backstageService.CourseManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseManageServiceImpl implements CourseManageService {
    @Autowired(required = false)
    StudyMajorCourseMapper studyMajorCourseMapper;
    @Autowired(required = false)
    StudyPublicCourseMapper studyPublicCourseMapper;
    @Autowired(required = false)
    TeachMajorCourseMapper teachMajorCourseMapper;
    @Autowired(required = false)
    TeacherPublicCourseMapper teacherPublicCourseMapper;
    @Autowired(required = false)
    MajorCourseAddressMapper majorCourseAddressMapper;
    @Autowired(required = false)
    PublicCourseAddressMapper publicCourseAddressMapper;
    @Autowired(required = false)
    MajorCourseMapper majorCourseMapper;
    @Autowired(required = false)
    PublicCourseMapper publicCourseMapper;
    @Override
    public Map<String, Object> importCourseInfo() {
        return null;
    }

    @Override
    public Map<String, Object> getCourseTableBySno(String sno) {
        Map<String, Object> info = new HashMap<>();
        // 专业课
        List<StudyMajorCourse> majorCourses = getStudyMajorCourse(sno);
        List<Map<String, Object>> cs = new ArrayList<>();
        for (StudyMajorCourse pc: majorCourses){
            List<MajorCourseAddress> courseAddress = getMajorCourseAddress(pc.getCno());
            MajorCourse majorCourse = getMajorCourse(pc.getCno());
            for (MajorCourseAddress ca:courseAddress){
                Map<String, Object> map = new HashMap<>();
                map.put("cname", majorCourse.getCname());
                map.put("address", ca.getAddress());
                map.put("time", ca.getTime());
                cs.add(map);
            }
        }
        info.put("major_course", cs);
        // 公共课
        List<StudyPublicCourse> publicCourses = getStudyPublicCourse(sno);
        List<Map<String, Object>> pcs = new ArrayList<>();
        for (StudyPublicCourse c:publicCourses){
            List<PublicCourseAddress> publicCourseAddress = getPublicCourseAddress(c.getCpno());
            PublicCourse publicCourse = getPublicCourse(c.getCpno());
            for (PublicCourseAddress pca:publicCourseAddress){
                Map<String, Object> map = new HashMap<>();
                map.put("cname", publicCourse.getCname());
                map.put("address", pca.getAddress());
                map.put("time", pca.getTime());
                pcs.add(map);
            }
        }
        info.put("public_course", pcs);
        return info;
    }

    @Override
    public Map<String, Object> getCourseTableByTno(String tno) {
        Map<String, Object> info = new HashMap<>();
        // 专业课教学详情
        List<TeachMajorCourse> teachMajorCourse = getTeachMajorCourse(tno);
        List<Map<String, Object>> cs = new ArrayList<>();
        for (TeachMajorCourse tmc:teachMajorCourse){
            List<MajorCourseAddress> courseAddress = getMajorCourseAddress(tmc.getCno());
            MajorCourse majorCourse = getMajorCourse(tmc.getCno());
            for (MajorCourseAddress ca:courseAddress){
                Map<String, Object> map = new HashMap<>();
                map.put("cname", majorCourse.getCname());
                map.put("address", ca.getAddress());
                map.put("time", ca.getTime());
                cs.add(map);
            }
        }
        info.put("major_course", cs);
        // 公共课教学详情
        List<TeachPublicCourse> teachPublicCourse = getTeachPublicCourse(tno);
        List<Map<String, Object>> pcs = new ArrayList<>();
        for (TeachPublicCourse c:teachPublicCourse){
            List<PublicCourseAddress> publicCourseAddress = getPublicCourseAddress(c.getCpno());
            PublicCourse publicCourse = getPublicCourse(c.getCpno());
            for (PublicCourseAddress pca:publicCourseAddress){
                Map<String, Object> map = new HashMap<>();
                map.put("cname", publicCourse.getCname());
                map.put("address", pca.getAddress());
                map.put("time", pca.getTime());
                pcs.add(map);
            }
        }
        info.put("public_course", pcs);
        return info;
    }

    public List<MajorCourseAddress> getMajorCourseAddress(Integer cno){
        QueryWrapper<MajorCourseAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("personCourseNo", cno);
        return majorCourseAddressMapper.selectList(queryWrapper);
    }

    public List<PublicCourseAddress> getPublicCourseAddress(Integer cno){
        QueryWrapper<PublicCourseAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("publicCourseNo", cno);
        return publicCourseAddressMapper.selectList(queryWrapper);
    }

    private List<StudyMajorCourse> getStudyMajorCourse(String sno){
        QueryWrapper<StudyMajorCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sno", sno);
        return studyMajorCourseMapper.selectList(queryWrapper);
    }

    private List<StudyPublicCourse> getStudyPublicCourse(String sno){
        QueryWrapper<StudyPublicCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sno", sno);
        return studyPublicCourseMapper.selectList(queryWrapper);
    }

    private List<TeachMajorCourse> getTeachMajorCourse(String tno){
        QueryWrapper<TeachMajorCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sno", tno);
        return teachMajorCourseMapper.selectList(queryWrapper);
    }

    private List<TeachPublicCourse> getTeachPublicCourse(String tno){
        QueryWrapper<TeachPublicCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sno", tno);
        return teacherPublicCourseMapper.selectList(queryWrapper);
    }

    private MajorCourse getMajorCourse(Integer cno){
        return majorCourseMapper.selectById(cno);
    }
    private PublicCourse getPublicCourse(Integer cno){
        return publicCourseMapper.selectById(cno);
    }
}
