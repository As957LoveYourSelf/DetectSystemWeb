<template>
  <!-- 主体内容 -->
  <div style="flex: 1;">
    <!-- 搜索栏 -->
    <div style="padding: 10px;">
      <!-- 搜索框 -->
      <el-select class="w-300" size="large" v-model="collage" placeholder="Select">
        <el-option
            v-for="item in collage_ops"
            :key="item.value"
            :value="item.value"
            :label="item.label"
            :disabled="item.disabled"
        />
      </el-select>
      <el-input
          v-model="unoInput"
          class="w-300 ml-8"
          size="large"
          placeholder="请输入教师编号"
          :suffix-icon="Search"
      />
      <el-input
          v-model="unameInput"
          class="w-300 ml-8"
          size="large"
          placeholder="请输入教师姓名"
          :suffix-icon="Search"
      />
      <!-- 按钮 -->
      <el-button class="ml-8" type="success" :icon="Search" @click="searchfn" round>搜索</el-button>
      <el-button class="ml-8" type="warning" :icon="Delete" @click="reset" round>重置</el-button>
    </div>
    <!-- 信息栏 -->
    <div style="min-height:87%; padding: 10px ">
      <el-table :data="tableData.slice((changePage.currentPage -1) * changePage.pageSize, changePage.currentPage*changePage.pageSize)" stripe border>

        <el-table-column prop="no" label="教工号"/> <!-- tno -->
        <el-table-column prop="title" label="职位" /> <!-- title -->
        <el-table-column prop="name" label="教师姓名"/> <!-- tname -->
        <el-table-column prop="collage" label="所属学院" /> <!-- collage -->
        <el-table-column prop="teachCourse" label="所教课程" /> <!-- teachCourse -->
        <el-table-column prop="operation" label="操作" >
          <template #default="scope">
            <el-button type="success" @click="getDetail(scope.row.no)" plain>查看</el-button>
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
  getTeacherDetail, selectByCollage,
  selectByCollageAndTname,
  selectByCollageAndTno,
  selectByTname,
  selectByTno,
  selectByTnoAndTName, selectByTnoAndTNameAndCollage
} from "@/utils/teacherManage";
let unoInput = ref('')
let unameInput = ref('')
//表单数据
let tableData = [
]
//这里是获取当前页数
const handleCurrentChange = (val)=> {
  changePage.currentPage = val
}
// 可选的学院数据
let collage = ref('请选择教师所在学院')
let collage_ops = []
getCollageSelector().then(res => {
  console.log(res)
  collage_ops = res
}).catch(err => {
  console.log(err)
})

// 查询函数
function searchfn() {
  if (unoInput.value === '' && unameInput.value === '' && collage.value === '请选择教师所在学院'){
    tableData = []
  }
  else if (unoInput.value === '' && unameInput.value === '' && collage.value !== '请选择教师所在学院'){
    const data = {collage:collage.value}
    selectByCollage(data).then(res => {
      console.log(res)
      tableData = res
    }).catch(err => {
      console.log(err)
    })
  }
  else if (unoInput.value !== '' && unameInput.value === '' && collage.value === '请选择教师所在学院'){
    const data = {tno:unoInput.value}
    selectByTno(data).then(res => {
      console.log(res)
      tableData = res
    }).catch(err => {
      console.log(err)
    })
  }
  else if (unoInput.value === '' && unameInput.value !== '' && collage.value !== '请选择教师所在学院'){
    const data = {tname:unameInput}
    selectByTname(data).then(res => {
      console.log(res)
      tableData = res
    }).catch(err => {
      console.log(err)
    })
  }else if (unoInput.value !== '' && unameInput.value !== '' && collage.value === '请选择教师所在学院'){
    const data = {tno:unoInput.value, tname:unameInput.value}
    selectByTnoAndTName(data).then(res => {
      console.log(res)
      tableData = res
    }).catch(err => {
      console.log(err)
    })
  }else if (unoInput.value === '' && unameInput.value !== '' && collage.value !== '请选择教师所在学院'){
    const data = {collage:collage.value, tname:unameInput.value}
    selectByCollageAndTname(data).then(res => {
      console.log(res)
      tableData = res
    }).catch(err => {
      console.log(err)
    })

  }else if (unoInput.value !== '' && unameInput.value === '' && collage.value !== '请选择教师所在学院'){
    const data = {collage:collage.value, tno:unoInput.value}
    selectByCollageAndTno(data).then(res => {
      console.log(res)
      tableData = res
    }).catch(err => {
      console.log(err)
    })
  }else if (unoInput.value !== '' && unameInput.value !== '' && collage.value !== '请选择教师所在学院'){
    const data = {tno:unoInput.value, tname:unameInput.value, collage:collage.value}
    selectByTnoAndTNameAndCollage(data).then(res => {
      console.log(res)
      tableData = res
    }).catch(err => {
      console.log(err)
    })
  }else {
    console.log("no data")
  }
}
// 重置函数
function reset() {
  unoInput = ref('')
  unameInput = ref('')
  collage = ref('请选择教师所在学院')
}
// 查看详情函数
function getDetail(tno){
  const data = {tno:tno}
  getTeacherDetail(data).then(res => {
    console.log(res)
    // 跳转至详情页面
  }).catch(err => {
    console.log(err)
  })
}
const changePage = reactive({
  currentPage:1, //默认当前页面为1
  pageSize:14,
  total: tableData.length, //总共有多少数据
  pageSizes: [10, 14]
})
</script>

