package com.postdesign.detectsystem.service.backstageService;

import java.util.List;
import java.util.Map;

public interface TeacherManageService {
    List<String> getCollageSelector();
    List<Map<String, Object>> selectByCollage(String collage);
    List<Map<String, Object>> selectByTno(String tno);
    List<Map<String, Object>> selectByTname(String tname);
    List<Map<String, Object>> selectByCollageAndTno(String collage, String tno);
    List<Map<String, Object>> selectByCollageAndTname(String collage, String tname);
    List<Map<String, Object>> selectByTnoAndTName(String tno, String tname);
    List<Map<String, Object>> selectByTnoAndTNameAndCollage(String tno, String tname, String collage);
    Map<String, Object> getTeacherDetail(String tno);

}
