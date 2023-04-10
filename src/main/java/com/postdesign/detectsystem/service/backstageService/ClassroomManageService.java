package com.postdesign.detectsystem.service.backstageService;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ClassroomManageService {
    List<String> getBuildingSelector();
    Integer getBuildingFloor(String buildingName);
    List<Map<String, Object>> getBuildingInfo(String buildingName, Integer isOrder, Integer floor);
    String order(String uid, String clsNo, String time);
    String deorder(String uid, String clsNo);
    List<Map<String, Object>> getOrderDetail(String clsNo);
}
