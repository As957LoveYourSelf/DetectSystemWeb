package com.postdesign.detectsystem.service.serviceImpl.backstageImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.postdesign.detectsystem.entity.Building;
import com.postdesign.detectsystem.mapper.BuildingMapper;
import com.postdesign.detectsystem.service.backstageService.ClassMangerService;
import com.postdesign.detectsystem.service.backstageService.ClassroomManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassroomServiceImpl implements ClassroomManageService {

    @Autowired(required = false)
    BuildingMapper buildingMapper;

    /**
     * 获取教学楼层选择器内容
     * */
    @Override
    public List<String> getBuildingInfo() {
        QueryWrapper<Building> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT bname");
        List<Building> buildings = buildingMapper.selectList(queryWrapper);
        if (!buildings.isEmpty()){
            List<String> bnames = new ArrayList<>();
            for (Building building: buildings){
                bnames.add(building.getBname());
            }
            return bnames;
        }
        return null;
    }

    /**
     * 根据教学楼名字获取其楼层数
     * */
    @Override
    public Integer getBuildingFloor(String buildingName) {
        QueryWrapper<Building> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bname", buildingName);
        Building building = buildingMapper.selectOne(queryWrapper);
        return building.getFloors();
    }
}
