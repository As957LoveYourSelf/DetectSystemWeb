<template>
  <div style="position: relative;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            margin: auto">
    <el-descriptions
        title="教职工信息"
        size="large"
        direction="vertical"
        :column="4"
        border
    >
      <div>
        <el-descriptions-item label="教师姓名">
          <el-tag size="large">{{info.name == null?'无': info.name}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="所属学院">
          <el-tag size="large">{{info.collage == null?'无': info.collage}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="年龄" :span="2">
          <el-tag size="large">{{info.age == null?'无': info.age}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="教学班级" :span="2">
          <el-tag size="large">{{info.classes == null?'无': info.classes}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="教学课程" :span="2">
          <el-tag size="large">{{info.courses == null?'无': info.courses}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="邮箱" :span="2">
          <el-tag size="large">{{info.email == null?'无': info.email}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="教工号" :span="2">
          <el-tag size="large">{{info.no == null?'无': info.no}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="电话">
          <el-tag size="large">{{info.phone == null?'无': info.phone}}</el-tag>
        </el-descriptions-item>
      </div>
    </el-descriptions>
  </div>
</template>

<script>
import {useRoute} from "vue-router";
export default {
  data(){
    return{
      info:[],
      route:useRoute()
    }
  },
  created() {
    this.info = JSON.parse(this.route.query.info)
  }
}
</script>
