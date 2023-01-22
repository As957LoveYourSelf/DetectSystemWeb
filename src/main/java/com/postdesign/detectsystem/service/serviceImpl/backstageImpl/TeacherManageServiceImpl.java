package com.postdesign.detectsystem.service.serviceImpl.backstageImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.postdesign.detectsystem.entity.Collage;
import com.postdesign.detectsystem.mapper.CollageMapper;
import com.postdesign.detectsystem.service.backstageService.TeacherManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TeacherManageServiceImpl implements TeacherManageService {

    @Autowired(required = false)
    CollageMapper collageMapper;
    /**
     * 获取教师管理页面教师所属学院选择器内容
     * */
    @Override
    public List<String> getCollageSelector() {
        QueryWrapper<Collage> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name");
        List<Collage> collages = collageMapper.selectList(queryWrapper);
        List<String> collageName = new ArrayList<>();
        for (Collage c: collages){
            collageName.add(c.getName());
        }
        return collageName;
    }

    @Override
    public List<Map<String, Object>> selectByCollage() {
        return null;
    }

    @Override
    public List<Map<String, Object>> selectByTno() {
        return null;
    }

    @Override
    public List<Map<String, Object>> selectByTname() {
        return null;
    }

    @Override
    public List<Map<String, Object>> selectByCollageAndTno() {
        return null;
    }

    @Override
    public List<Map<String, Object>> selectByCollageAndTname() {
        return null;
    }

    @Override
    public List<Map<String, Object>> selectByTnoAndTName() {
        return null;
    }

    @Override
    public List<Map<String, Object>> selectByTnoAndTNameAndCollage() {
        return null;
    }
}
