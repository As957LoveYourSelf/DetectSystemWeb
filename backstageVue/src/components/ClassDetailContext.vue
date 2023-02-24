<template>
  <div style="margin: 20px;flex: 1">
    <el-descriptions
        title="班级信息"
        direction="vertical"
        :column="5"
        size="large"
        border
    >
      <div style="width: 80vh" >
        <el-descriptions-item label="班级名称">
          <el-tag effect="dark" size="large">{{ info.name }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="所属学院">
          <el-tag effect="dark" size="large">{{ info.collage }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="所属专业">
          <el-tag effect="dark" size="large">{{ info.major }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="班级人数">
          <el-tag effect="dark" size="large">{{ tableData.length }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="班主任">
          <el-tag effect="dark" size="large">{{ info.headmaster }}</el-tag>
        </el-descriptions-item>
      </div>
    </el-descriptions>
    <div style="margin-top: 10px;">
      <el-tag effect="dark" type="success" size="large">班级成员</el-tag>
      <el-table
          :data="tableData"
          height="500"
          style="width: 100%; height: 100%"
          stripe border>
        <el-table-column type="index" width="50px" />
        <el-table-column prop="class" label="所属班级" />
        <el-table-column prop="name" label="姓名"/>
        <el-table-column prop="sno" label="学号"/>
        <el-table-column prop="major" label="专业"/>
        <el-table-column prop="collage" label="学院" />
        <el-table-column prop="operation" label="操作" >
          <template #default="scope">
            <el-button type="success" plain>查看</el-button>
            <el-button type="danger" plain>移除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import {useRoute} from "vue-router";
import {getClassDetail} from "../utils/classManage";
//这里是获取当前页数
export default {
  data(){
    return{
      data:{className:''},
      route: useRoute(),
      info:[],
      tableData:[]
    }
  },
  created() {
    this.data.className = this.route.query.classname
    // console.log(this.data)
    getClassDetail(this.data).then(res => {
      this.info = res.data
      this.tableData = res.data.people
      console.log(this.info)
    }).catch(err => {
      console.log(err)
    })
  }
}
</script>
