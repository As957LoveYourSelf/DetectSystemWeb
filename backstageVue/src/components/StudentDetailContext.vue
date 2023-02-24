<template>
  <div style="position: relative;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            margin: auto">
    <el-descriptions
        title="学生信息"
        direction="vertical"
        :column="4"
        size="large"
        border
    >
      <div>
        <el-descriptions-item label="学生姓名">
          <el-tag size="large">{{info.name == null?'无': info.name}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="学号">
          <el-tag size="large">{{info.no == null?'无': info.no}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="所属学院">
          <el-tag size="large">{{info.collage == null?'无': info.collage}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="专业" :span="2">
          <el-tag size="large">{{info.major == null?'无': info.major}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="年级" :span="2">
          <el-tag size="large">
            {{info.grade == null?'无': info.grade}}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="班级" :span="2">
          <el-tag size="large">
            {{info.class == null?'无': info.class}}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="年龄" :span="1">
          <el-tag size="large">{{info.age == null?'无': info.age}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="邮箱" :span="1">
          <el-tag size="large">{{info.email == null?'无': info.email}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="电话" :span="2">
          <el-tag size="large">{{info.phone == null?'无': info.phone}}</el-tag>
        </el-descriptions-item>
      </div>
    </el-descriptions>
  </div>
</template>

<script>
import {useRoute} from "vue-router";
import {getStudentDetail} from "../utils/studentManage";
export default {
  data(){
    return{
      info:[],
      route:useRoute(),
      detailData:{sno:''},
    }
  },
  created() {
    this.detailData.sno = this.route.query.sno
    getStudentDetail(this.detailData).then(res => {
      this.info = res.data
    }).catch(err => {
      console.log(err)
    })
  }
}
</script>
