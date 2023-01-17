<template>
  <!-- 主体内容 -->
  <div style="flex: 1; ">
    <!-- 搜索栏 -->
    <div style="padding: 10px">
      <!-- 搜索框 -->
      <el-input
          v-model="unoInput"
          class="w-300 ml-5"
          size="large"
          placeholder="请输入学号"
          :suffix-icon="Search"
      />
      <el-input
          v-model="unameInput"
          class="w-300 ml-8"
          size="large"
          placeholder="请输入姓名"
          :suffix-icon="Search"
      />
      <!-- 按钮 -->
      <el-button class="ml-8" type="success" :icon="Search" round>搜索</el-button>
      <el-button class="ml-8" type="warning" :icon="Delete" round>重置</el-button>
    </div>
    <!-- 信息栏 -->
    <div>
      <el-table :data="tableData" stripe>
        <el-table-column prop="class" label="班级" width="180" />
        <el-table-column prop="name" label="姓名" width="180" />
        <el-table-column prop="address" label="学院" />
        <el-table-column prop="operation" label="操作" />
      </el-table>
    </div>
  </div>
</template>

<script setup>
import {
  Delete,
  Search,
} from '@element-plus/icons-vue'
  import { ref } from 'vue'
  const unoInput = ref('')
  const unameInput = ref('')

const tableData = [
  {
    class: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    class: '2016-05-02',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    class: '2016-05-04',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    class: '2016-05-01',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
]
</script>

