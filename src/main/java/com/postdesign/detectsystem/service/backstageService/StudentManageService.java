package com.postdesign.detectsystem.service.backstageService;

import java.util.List;
import java.util.Map;

public interface StudentManageService {
    Map<String, Object> selectBySno(String sno);
    List<Map<String, Object>> selectBySname(String sname);
    Map<String, Object> selectBySNoAndSName(String sno, String sname);
}
