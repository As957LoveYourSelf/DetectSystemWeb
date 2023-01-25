package com.postdesign.detectsystem.service.backstageService;

import java.util.List;
import java.util.Map;

public interface StudentManageService {
    List<Map<String, Object>> selectBySno(String sno);
    List<Map<String, Object>> selectBySname(String sname);
    List<Map<String, Object>> selectBySNoAndSName(String sno, String sname);
    Map<String, Object> getStudentDetail(String sno);
}
