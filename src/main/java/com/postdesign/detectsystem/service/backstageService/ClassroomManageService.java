package com.postdesign.detectsystem.service.backstageService;

import java.util.List;

public interface ClassroomManageService {
    List<String> getBuildingInfo();
    Integer getBuildingFloor(String buildingName);
}
