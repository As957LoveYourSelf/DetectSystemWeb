package com.postdesign.detectsystem.service.backstageService;

import java.util.List;
import java.util.Map;

public interface StudentManageService {
    List<Map<String, Object>> select(String sno, String sname);
    Map<String, Object> getStudentDetail(String sno);
    String removeStudent(String sno);
}
