package com.postdesign.detectsystem.service.serviceImpl.backstageImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.postdesign.detectsystem.entity.*;
import com.postdesign.detectsystem.mapper.*;
import com.postdesign.detectsystem.service.backstageService.CourseManageService;
import com.postdesign.detectsystem.utils.JSONResult;
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
    @Autowired(required = false)
    GradeMapper gradeMapper;
    @Autowired(required = false)
    CollegeMapper collegeMapper;

    @Autowired(required = false)
    MajorMapper majorMapper;



    @Override
    public String deleteCourse(String cno, String courseType) {
        if (courseType.equals("public")){
            publicCourseMapper.deleteById(cno);
            return "success";
        }
        if (courseType.equals("major")){
            majorCourseMapper.deleteById(cno);
            return "success";
        }
        return "error";
    }

    @Override
    public JSONResult<String> importMajorCourseInfo(MajorCourse majorCourse) {
        try {
            majorCourseMapper.insert(majorCourse);
            return new JSONResult<>("success");
        }catch (Exception e){
            return new JSONResult<>(500,"error",null);
        }
    }

    @Override
    public JSONResult<String> importPublicCourseInfo(PublicCourse publicCourse) {
        try {
            publicCourseMapper.insert(publicCourse);
            return new JSONResult<>("success");
        }catch (Exception e){
            return new JSONResult<>(500,"error",null);
        }
    }

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

    @Override
    public List<String> getMajors(String college) {
        QueryWrapper<Major> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("college", college);
        List<Major> majors = majorMapper.selectList(queryWrapper);
        List<String> info = new ArrayList<>();
        for (Major m:majors){
            info.add(m.getMajorName());
        }
        return info;
    }

    /**
     *         <el-table-column prop="cname" label="课程名称" />
     *         <el-table-column prop="major" label="所属专业"/>
     *         <el-table-column prop="college" label="所属学院" />
     * */
    @Override
    public List<Map<String, Object>> select(String college, String major,  Integer grade, String type) {
        if (college == null || major == null || type == null){
            return null;
        }
        QueryWrapper<MajorCourse> majorCourseQueryWrapper = new QueryWrapper<>();
        QueryWrapper<PublicCourse> publicCourseQueryWrapper = new QueryWrapper<>();
        if (type.equals("all")){
            if (college.equals("") && major.equals("") && grade == null){
                return getCourses(null, null);
            }else if(!college.equals("") && major.equals("") && grade == null){
                majorCourseQueryWrapper.eq("college", college);
                publicCourseQueryWrapper.eq("college", college);
                return getCourses(majorCourseQueryWrapper, publicCourseQueryWrapper);
            } else if (college.equals("") && major.equals("")) {
                majorCourseQueryWrapper.eq("grade", grade);
                publicCourseQueryWrapper.eq("grade", grade);
                return getCourses(majorCourseQueryWrapper, publicCourseQueryWrapper);
            } else if (!college.equals("") && !major.equals("") && grade == null) {
                majorCourseQueryWrapper.eq("major", major).eq("college", college);
                publicCourseQueryWrapper.eq("college", college);
                return getCourses(majorCourseQueryWrapper, publicCourseQueryWrapper);
            } else {
                majorCourseQueryWrapper.eq("major", major).eq("college", college).eq("grade",grade);
                publicCourseQueryWrapper.eq("college", college).eq("grade",grade);
                return getCourses(majorCourseQueryWrapper, publicCourseQueryWrapper);
            }
        }
        else if (type.equals("public")){
            if (college.equals("") && major.equals("") && grade == null){
                return getPublicCourses( null);
            }else if(!college.equals("") && major.equals("") && grade == null){
                publicCourseQueryWrapper.eq("college", college);
                return getPublicCourses(publicCourseQueryWrapper);
            } else if (!college.equals("") && major.equals("")) {
                publicCourseQueryWrapper.eq("college", college).eq("grade", grade);
                return getPublicCourses(publicCourseQueryWrapper);
            }else if (college.equals("") && major.equals("")) {
                publicCourseQueryWrapper.eq("grade", grade);
                return getPublicCourses(publicCourseQueryWrapper);
            } else if (!college.equals("") && grade == null) {
                publicCourseQueryWrapper.eq("college", college);
                return getPublicCourses(publicCourseQueryWrapper);
            } else {
                publicCourseQueryWrapper.eq("college", college).eq("grade", grade);
                return getPublicCourses(publicCourseQueryWrapper);
            }
        } else if (type.equals("major")) {
            if (college.equals("") && major.equals("") && grade == null){
                return getMajorCourses( null);
            }else if(!college.equals("") && major.equals("") && grade == null){
                majorCourseQueryWrapper.eq("college", college);
                return getMajorCourses(majorCourseQueryWrapper);
            } else if(!college.equals("") && major.equals("")){
                majorCourseQueryWrapper.eq("college", college).eq("grade", grade);
                return getMajorCourses(majorCourseQueryWrapper);
            }else if (college.equals("") && major.equals("")) {
                majorCourseQueryWrapper.eq("grade", grade);
                return getMajorCourses(majorCourseQueryWrapper);
            } else if (!college.equals("") && grade == null) {
                majorCourseQueryWrapper.eq("major", major).eq("college", college);
                return getMajorCourses(majorCourseQueryWrapper);
            } else {
                majorCourseQueryWrapper.eq("major", major).eq("college", college).eq("grade",grade);
                return getMajorCourses(majorCourseQueryWrapper);
            }
        }else {
            return null;
        }
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

    @Override
    public Map<String, Object> getCourseDetail(String cno, String type) {
//        if (type.equals("public")) {
//            QueryWrapper<>
//        }
//        if (type.equals("major")){
//
//        }
        return null;
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
        queryWrapper.eq("tno", tno);
        return teachMajorCourseMapper.selectList(queryWrapper);
    }

    private List<TeachPublicCourse> getTeachPublicCourse(String tno){
        QueryWrapper<TeachPublicCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tno", tno);
        return teacherPublicCourseMapper.selectList(queryWrapper);
    }

    private MajorCourse getMajorCourse(Integer cno){
        return majorCourseMapper.selectById(cno);
    }

    private List<Map<String, Object>> getMajorCourses(QueryWrapper<MajorCourse> queryWrapper){
        List<MajorCourse> majorCourses = majorCourseMapper.selectList(queryWrapper);
        List<Map<String, Object>> info = new ArrayList<>();
        for (MajorCourse mc:majorCourses){
            Map<String, Object> map = new HashMap<>();
            map.put("cno", mc.getCno());
            map.put("cname", mc.getCname());
            map.put("major", mc.getMajor());
            map.put("college", mc.getCollege());
            map.put("grade",mc.getGrade());
            map.put("type", "专业课");
            info.add(map);
        }
        return info;
    }

    private List<Map<String, Object>> getPublicCourses(QueryWrapper<PublicCourse> queryWrapper){
        List<PublicCourse> publicCourses = publicCourseMapper.selectList(queryWrapper);
        List<Map<String, Object>> info = new ArrayList<>();
        for (PublicCourse pc:publicCourses){
            Map<String, Object> map = new HashMap<>();
            map.put("cno", pc.getCno());
            map.put("cname", pc.getCname());
            map.put("major", "无");
            map.put("college", pc.getCollege());
            map.put("grade", pc.getGrade());
            map.put("type", "公共课");
            info.add(map);
        }
        return info;
    }

    private List<Map<String, Object>> getCourses(QueryWrapper<MajorCourse> majorCs, QueryWrapper<PublicCourse> publicCs){
        List<MajorCourse> majorCourses = majorCourseMapper.selectList(majorCs);
        List<PublicCourse> publicCourses = publicCourseMapper.selectList(publicCs);
        List<Map<String, Object>> info = new ArrayList<>();
        for (MajorCourse mc:majorCourses){
            Map<String, Object> map = new HashMap<>();
            map.put("cno", mc.getCno());
            map.put("cname", mc.getCname());
            map.put("college", mc.getCollege());
            map.put("major", mc.getMajor());
            map.put("grade", mc.getGrade());
            map.put("type", "专业课");
            info.add(map);
        }
        for (PublicCourse pc:publicCourses){
            Map<String, Object> map = new HashMap<>();
            map.put("cno", pc.getCno());
            map.put("cname", pc.getCname());
            map.put("major", "无");
            map.put("college", pc.getCollege());
            map.put("grade", pc.getGrade());
            map.put("type", "公共课");
            info.add(map);
        }
        return info;
    }

    private PublicCourse getPublicCourse(Integer cno){
        return publicCourseMapper.selectById(cno);
    }
}
