package com.postdesign.detectsystem.service.backstageService;

import java.util.List;
import java.util.Map;

public interface ClassMangerService {
    List<Integer> getGradeSelector();
    List<String> getCollegeSelector();
    List<Map<String, Object>> select(Integer grade, String collage);
    Map<String, Object> getClassDetail(String clsName);
    List<String> getClasses(String college, String major, Integer grade);
    String addClass(String classname, String college, Integer grade, String major, String headmaster);
    Integer getClassNo(String college, String major, Integer grade);
}
