package com.postdesign.detectsystem.service.serviceImpl.backstageImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.postdesign.detectsystem.entity.Building;
import com.postdesign.detectsystem.entity.OrderRoom;
import com.postdesign.detectsystem.entity.User;
import com.postdesign.detectsystem.mapper.BuildingMapper;
import com.postdesign.detectsystem.mapper.OrderRoomMapper;
import com.postdesign.detectsystem.mapper.UserMapper;
import com.postdesign.detectsystem.service.backstageService.ClassroomManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClassroomServiceImpl implements ClassroomManageService {

    @Autowired(required = false)
    BuildingMapper buildingMapper;
    @Autowired(required = false)
    OrderRoomMapper orderRoomMapper;
    @Autowired(required = false)
    UserMapper userMapper;


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

        if (isOrder != 2){
            if (buildingName.equals("") && floor == null){
                queryWrapper.eq("isOrder", isOrder);
                return selectInfo(queryWrapper);
            }else if (!buildingName.equals("") && floor == null){
                queryWrapper.eq("bname", buildingName).eq("isOrder", isOrder);
                return selectInfo(queryWrapper);
            } else if (!buildingName.equals("")) {
                queryWrapper.eq("bname", buildingName).eq("isOrder", isOrder).like("clsNo", "-"+ floor);
                return selectInfo(queryWrapper);
            }else {
                return null;
            }
        }else {
            if (buildingName.equals("") && floor == null){;
                return selectInfo(null);
            }else if (!buildingName.equals("") && floor == null){
                queryWrapper.eq("bname", buildingName);
                return selectInfo(queryWrapper);
            } else if (!buildingName.equals("")) {
                queryWrapper.eq("bname", buildingName).like("clsNo", "-"+ floor);
                return selectInfo(queryWrapper);
            }else {
                return null;
            }
        }
    }

    @Override
    public String order(String uid, String clsNo, String time) {
        Building building = buildingMapper.selectById(clsNo);
        User user = userMapper.selectById(uid);
        if (user == null){
            return "userNotFound";
        }
        if (building == null){
            return "selectError";
        }
        try {
            building.setIsOrder(1);
            buildingMapper.updateById(building);
            OrderRoom orderRoom = new OrderRoom();
            orderRoom.setTime(time);
            orderRoom.setClsNo(clsNo);
            orderRoom.setUno(uid);
            orderRoomMapper.insert(orderRoom);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    @Override
    public String deorder(String uid, String clsNo) {
        Building building = buildingMapper.selectById(clsNo);
        if (building == null){
            return "selectError";
        }
        try {
            building.setIsOrder(0);
            buildingMapper.updateById(building);
            QueryWrapper<OrderRoom> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uno", uid).eq("clsNo", clsNo);
            orderRoomMapper.delete(queryWrapper);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

    @Override
    public List<Map<String, Object>> getOrderDetail(String clsNo) {
        QueryWrapper<OrderRoom> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("clsNo", clsNo);
        List<OrderRoom> orderRooms = orderRoomMapper.selectList(queryWrapper);
        List<Map<String, Object>> data = new ArrayList<>();
        for (OrderRoom o:orderRooms){
            Map<String, Object> map = new HashMap<>();
            map.put("clsNo", o.getClsNo());
            map.put("time", o.getTime());
            map.put("uno", o.getUno());
            data.add(map);
        }
        return data;
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
