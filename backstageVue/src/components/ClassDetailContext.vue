<template>
  <div style="margin: 20px;flex: 1">
    <el-descriptions
        title="班级信息"
        direction="vertical"
        :column="5"
        :size="size"
        border
    >
      <div style="width: 80vh">
        <el-descriptions-item label="班级名称" v-model="data.name"></el-descriptions-item>
        <el-descriptions-item label="所属学院" v-model="data.collage"></el-descriptions-item>
        <el-descriptions-item label="所属专业" v-model="data.major"></el-descriptions-item>
        <el-descriptions-item label="班级人数" v-model="data.peoplenum" ></el-descriptions-item>
        <el-descriptions-item label="班主任" v-model="data.headmaster"></el-descriptions-item>
      </div>
    </el-descriptions>
    <div style="margin-top: 10px;">
      <el-tag effect="dark" type="success" size="large">班级成员</el-tag>
      <el-table :data="data.people.slice((changePage.currentPage -1) * changePage.pageSize, changePage.currentPage*changePage.pageSize)" max-height="70px" stripe border>
        <el-table-column type="index" width="50px" />
        <el-table-column prop="class" label="所属班级" />
        <el-table-column prop="name" label="姓名"/>
        <el-table-column prop="sno" label="学号"/>
        <el-table-column prop="major" label="专业"/>
        <el-table-column prop="collage" label="学院" />
        <el-table-column prop="operation" label="操作" >
          <template #default="scope">
            <el-button type="success" plain>查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from 'vue'
const changePage = reactive({
  currentPage:1, //默认当前页面为1
  pageSize:14,
  total:20, //总共有多少数据
  pageSizes: [10, 14]
})
//这里是获取当前页数
const size = ref('large');
const data = []
</script>
