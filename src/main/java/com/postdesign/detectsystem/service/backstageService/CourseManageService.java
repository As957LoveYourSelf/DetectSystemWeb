package com.postdesign.detectsystem.service.backstageService;

import java.util.List;
import java.util.Map;

public interface CourseManageService {
    String importCourseInfo();
    List<Integer> getGradeSelector();
    List<String> getCollegeSelector();
    List<String> getMajors(String collage);
    List<Map<String, Object>> select(String sno, String major, Integer grade, String type);
    String deleteCourse(String cno, String courseType);
    Map<String, Object> getCourseTableBySno(String sno);
    Map<String, Object> getCourseTableByTno(String tno);
    Map<String, Object> getCourseDetail(String cno, String type);
}
