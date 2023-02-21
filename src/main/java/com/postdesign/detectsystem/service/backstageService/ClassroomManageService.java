package com.postdesign.detectsystem.service.backstageService;

import java.util.List;
import java.util.Map;

public interface ClassroomManageService {
    List<String> getBuildingSelector();
    Integer getBuildingFloor(String buildingName);
    List<Map<String, Object>> getBuildingInfo(String buildingName, Integer isOrder, Integer floor);
    String order(String clsNo);
    String deorder(String clsNo);
}
