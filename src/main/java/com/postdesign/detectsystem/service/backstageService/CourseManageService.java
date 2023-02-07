package com.postdesign.detectsystem.service.backstageService;

import java.util.Map;

public interface CourseManageService {
    Map<String, Object> importCourseInfo();

    Map<String, Object> getCourseTableBySno(String sno);
    Map<String, Object> getCourseTableByTno(String tno);
}
