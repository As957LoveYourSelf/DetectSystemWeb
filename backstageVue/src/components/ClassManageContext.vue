<template>
  <!-- 主体内容 -->
  <div style="flex: 1;">
    <!-- 搜索栏 -->
    <div style="padding: 10px;">
      <!-- 搜索框 -->
      <el-select class="w-300" size="large" v-model="college" placeholder="Select">
        <el-option
            v-for="item in college_ops"
            :key="item.value"
            :value="item.value"
            :disabled="item.disabled"
        />
      </el-select>
      <el-select class="w-300 ml-8" size="large" v-model="level" placeholder="Select">
        <el-option
            v-for="item in level_ops"
            :key="item.value"
            :value="item.value"
            :disabled="item.disabled"
        />
      </el-select>
      <!-- 按钮 -->
      <el-button class="ml-8" type="success" :icon="Search" @click="search(level, college)" round>搜索</el-button>
      <el-button class="ml-8" type="warning" :icon="Delete" @click="reset" round>重置</el-button>
    </div>
    <!-- 信息栏 -->
    <div style="min-height:87%; padding: 10px ">
      <el-table :data="tableData.slice((changePage.currentPage -1) * changePage.pageSize, changePage.currentPage*changePage.pageSize)" stripe border>
        <el-table-column prop="collage" label="学院" />
        <el-table-column prop="major" label="专业"/>
        <el-table-column prop="grade" label="年级" />
        <el-table-column prop="class" label="班级名称" />
        <el-table-column prop="headmaster" label="班主任" />
        <el-table-column prop="operation" label="操作" >
          <template #default="scope">
            <el-button type="success" @click="" plain>查看</el-button>
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
import {
  Delete,
  Search,
} from '@element-plus/icons-vue'
import { ref, reactive } from 'vue'
import {
  getCollageSelector,
  getGradeSelector,
  selectByCollage,
  selectByGrade,
  selectByGradeAndCollage
} from "@/utils/classManage";
//这里是获取当前页数
const handleCurrentChange = (val)=> {
  changePage.currentPage = val
}
// 可选的年级数据
let level = ref('请选择年级')
let level_ops  = null
getGradeSelector().then(res => {
  console.log(res)
  level_ops = res
}).catch(err => {
  console.log(err)
})

// 可选的学院数据
let college = ref('请选择学院')
let college_ops = null
getCollageSelector().then(res => {
  console.log(res)
  college_ops = res
}).catch(err => {
  console.log(err)
})

// 表格数据
let tableData = []
// 搜索函数
function search(grade, collage) {
  console.log(grade, collage)
  let params = {
    grade: "",
    collage: ""
  }
  if (grade === '请选择年级' && collage === '请选择学院'){
    tableData = []
  }
  else if (grade !== '请选择年级' && collage === '请选择学院'){
    params.grade = grade.toInteger
    params.collage = ""
    selectByGrade(params).then(res => {
      console.log(res)
      tableData = res
    }).catch(err => {
      console.log(err)
    })
  }
  else if (grade === '请选择年级' && collage !== '请选择学院'){
    params.grade = ""
    params.collage = collage
    selectByCollage(params).then(res => {
      console.log(res)
      tableData = res
    })
        .catch(err => {
          console.log(err)
        })
  }
  else if (grade !== '请选择年级' && collage !== '请选择学院'){
    params.grade = grade.toInteger
    params.collage = collage
    selectByGradeAndCollage(params).then(res => {
      console.log(res)
      tableData = res
    })
        .catch(err => {
          console.log(err)
        })
  }else {
    console.log("nodata")
  }
}
// 重置函数
function reset() {
  console.log("reset")
  level = ref('请选择年级')
  college = ref('请选择学院')
  tableData = []
}
// 查看详情
function getClassDetail(){

}
const changePage = reactive({
  currentPage:1, //默认当前页面为1
  pageSize:14,
  total: tableData.length, //总共有多少数据
  pageSizes: [10, 14]
})

</script>

