package com.postdesign.detectsystem.service.backstageService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.postdesign.detectsystem.entity.MajorCourse;
import com.postdesign.detectsystem.entity.PublicCourse;
import com.postdesign.detectsystem.utils.JSONResult;

import java.util.List;
import java.util.Map;

public interface CourseManageService{
    JSONResult<String> importMajorCourseInfo(MajorCourse majorCourse);
    JSONResult<String> importPublicCourseInfo(PublicCourse publicCourse);
    List<Integer> getGradeSelector();
    List<String> getCollegeSelector();
    List<String> getMajors(String collage);
    List<Map<String, Object>> select(String sno, String major, Integer grade, String type);
    String deleteCourse(String cno, String courseType);
    Map<String, Object> getCourseTableBySno(String sno);
    Map<String, Object> getCourseTableByTno(String tno);
    Map<String, Object> getCourseDetail(String cno, String type);
}
