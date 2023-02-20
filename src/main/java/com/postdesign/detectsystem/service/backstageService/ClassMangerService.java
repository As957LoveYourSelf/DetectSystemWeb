package com.postdesign.detectsystem.service.backstageService;

import java.util.List;
import java.util.Map;

public interface ClassMangerService {
    List<Integer> getGradeSelector();
    List<String> getCollageSelector();
    List<Map<String, Object>> select(Integer grade, String collage);
    Map<String, Object> getClassDetail(String clsName);
}
