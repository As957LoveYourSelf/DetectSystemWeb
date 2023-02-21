package com.postdesign.detectsystem.service.serviceImpl.backstageImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.postdesign.detectsystem.entity.Building;
import com.postdesign.detectsystem.mapper.BuildingMapper;
import com.postdesign.detectsystem.service.backstageService.ClassMangerService;
import com.postdesign.detectsystem.service.backstageService.ClassroomManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassroomServiceImpl implements ClassroomManageService {

    @Autowired(required = false)
    BuildingMapper buildingMapper;

    /**
     * 获取教学楼层选择器内容
     * */
    @Override
    public List<String> getBuildingSelector() {
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
        Building building = buildingMapper.selectList(queryWrapper).get(0);
        return building.getFloors();
    }
    /**
     * 查询教室信息
     * TODO: 课室所在楼层的模糊匹配
     * */
    @Override
    public List<Map<String, Object>> getBuildingInfo(String buildingName, Integer isOrder, Integer floor) {
        if(buildingName == null){
            return null;
        }
        QueryWrapper<Building> queryWrapper = new QueryWrapper<>();
        if (buildingName.equals("") && floor == null){
            queryWrapper.eq("isOrder", isOrder);
            return selectInfo(queryWrapper);
        }else if (!buildingName.equals("") && floor == null){
            queryWrapper.eq("bname", buildingName).eq("isOrder", isOrder);
            return selectInfo(queryWrapper);
        } else if (!buildingName.equals("")) {
            queryWrapper.eq("bname", buildingName).eq("isOrder", isOrder).eq("clsNo", floor).like("clsNo", "-"+ floor);
            return selectInfo(queryWrapper);
        }else {
            return null;
        }
    }

    @Override
    public String order(String clsNo) {
        Building building = buildingMapper.selectById(clsNo);
        if (building == null){
            return "selectError";
        }
        building.setIsOrder(1);
        buildingMapper.updateById(building);
        return "success";
    }

    @Override
    public String deorder(String clsNo) {
        Building building = buildingMapper.selectById(clsNo);
        if (building == null){
            return "selectError";
        }
        building.setIsOrder(0);
        buildingMapper.updateById(building);
        return "success";
    }

    /**
     * <el-table-column prop="building" label="所属教学楼" />
     * <el-table-column prop="roomNo" label="课室编号"/>
     * <el-table-column prop="isOrder" label="是否被预约 />
     * <el-table-column prop="operation" label="操作" >
     * */
    private List<Map<String, Object>> selectInfo(QueryWrapper<Building> queryWrapper){
        List<Building> buildings = buildingMapper.selectList(queryWrapper);
        List<Map<String, Object>> info = new ArrayList<>();
        for (Building building:buildings){
            Map<String, Object> map = new HashMap<>();
            map.put("building", building.getBname());
            map.put("roomNo", building.getClsNo());
            map.put("isOrder", building.getIsOrder() == 0 ? "NO" : "YES");
            info.add(map);
        }
        return info;
    }
}
