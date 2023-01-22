package com.postdesign.detectsystem.service.backstageService;

import java.util.List;
import java.util.Map;

public interface ClassMangerService {
    List<Integer> getGradeSelector();
    List<String> getCollageSelector();

    List<Map<String, Object>> selectByGrade(Integer grade);

    List<Map<String, Object>> selectByCollage(String collage);

    List<Map<String, Object>> selectByGradeAndCollage(Integer grade, String collage);

    List<Map<String, Object>> getClassInfo(String className);
}
