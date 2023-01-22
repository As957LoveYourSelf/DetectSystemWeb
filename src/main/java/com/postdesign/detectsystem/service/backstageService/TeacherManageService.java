package com.postdesign.detectsystem.service.backstageService;

import java.util.List;
import java.util.Map;

public interface TeacherManageService {
    List<String> getCollageSelector();

    List<Map<String, Object>> selectByCollage();
    List<Map<String, Object>> selectByTno();
    List<Map<String, Object>> selectByTname();
    List<Map<String, Object>> selectByCollageAndTno();
    List<Map<String, Object>> selectByCollageAndTname();
    List<Map<String, Object>> selectByTnoAndTName();
    List<Map<String, Object>> selectByTnoAndTNameAndCollage();
}
