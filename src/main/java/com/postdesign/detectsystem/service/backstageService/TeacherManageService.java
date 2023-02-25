package com.postdesign.detectsystem.service.backstageService;

import java.util.List;
import java.util.Map;

public interface TeacherManageService {
    List<String> getCollegeSelector();

    List<Map<String, Object>> select(String tno, String tname, String collage);
    Map<String, Object> getTeacherDetail(String tno);

}
