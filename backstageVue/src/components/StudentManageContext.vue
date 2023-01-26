<template>
  <!-- 主体内容 -->
  <div style="flex: 1;">
    <!-- 搜索栏 -->
    <div style="padding: 10px;">
      <!-- 搜索框 -->
      <el-input
          v-model="unoInput"
          class="w-300"
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
      <el-button class="ml-8" type="success" :icon="Search" @click="searchfn" round>搜索</el-button>
      <el-button class="ml-8" type="warning" :icon="Delete" @click="reset" round>重置</el-button>
    </div>
    <!-- 信息栏 -->
    <div style="min-height:87%; padding: 10px ">
      <el-table :data="tableData.slice((changePage.currentPage -1) * changePage.pageSize, changePage.currentPage*changePage.pageSize)" stripe border>
        <el-table-column prop="class" label="所属班级" />
        <el-table-column prop="name" label="姓名"/>
        <el-table-column prop="sno" label="学号"/>
        <el-table-column prop="major" label="专业"/>
        <el-table-column prop="collage" label="学院" />
        <el-table-column prop="operation" label="操作" >
          <template #default="scope">
            <el-button type="success" @click="getDetail(scope.row.sno)" plain>查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页栏 -->
    <div style="padding: 10px">
      <el-pagination
          v-model:current-page="changePage.currentPage"
          v-model:page-size="changePage.pageSize"
          v-model:page-sizes="changePage.pageSizes"
          @current-change="handleCurrentChange"
          :background="true"
          layout="total, sizes, prev, pager, next, jumper"
          :total="changePage.total"
      />
    </div>
  </div>
</template>

<script setup>
import {getStudentDetail, selectBysname, selectBysno, selectBysnoAndsname} from "@/utils/studentManage";

let tableData = [

]
import {
  Delete,
  Search,
} from '@element-plus/icons-vue'
import { ref, reactive } from 'vue'
let unoInput = ref('')
let unameInput = ref('')
const changePage = reactive({
  currentPage:1, //默认当前页面为1
  pageSize:14,
  total: tableData.length, //总共有多少数据
  pageSizes: [10, 14]
})
//这里是获取当前页数
const handleCurrentChange = (val)=> {
  changePage.currentPage = val
}
// 搜索函数
function searchfn() {
  console.log(unameInput,unoInput)

  if (unoInput.value === "" && unameInput.value === ""){
    tableData = []
  }
  else if (unoInput.value !== "" && unameInput.value === ""){
    const data = {sno:unoInput.value}
    selectBysno(data).then(res => {
      console.log(res)
      tableData = res
    }).catch(err => {
      console.log(err)
    })
  }else if (unoInput.value === "" && unameInput.value !== ""){
    const data = {sname:unameInput.value}
    selectBysname(data).then(res => {
      console.log(res)
      tableData = res
    }).catch(err => {
      console.log(err)
    })
  }else if (unoInput.value !== "" && unameInput.value !== ""){
    const data = {sno:unoInput.value, sname:unameInput.value}
    selectBysnoAndsname(data).then(res => {
      console.log(res)
      tableData = res
    }).catch(err => {
      console.log(err)
    })
  }

}
// 重置函数
function reset() {
  unoInput = ref('')
  unameInput = ref('')
}
// 查看学生详情
function getDetail(sno) {
  // 跳转学生页面
  const data = {sno: sno}
  getStudentDetail(data).then(res => {
    console.log(res)
  }).catch(err => {
    console.log(err)
  })
}

</script>

