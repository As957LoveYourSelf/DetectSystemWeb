<template>
  <div style="flex: 1; padding: 10px">
    <div style="min-height:90%; padding: 10px ">
      <el-table :data="tableData.slice((changePage.currentPage -1) * changePage.pageSize, changePage.currentPage*changePage.pageSize)"
                stripe
                border
                @selection-change="handleSelectionChange"
                >
        <el-table-column type="selection" width="39" />
        <el-table-column prop="class" label="请求主题" />
        <el-table-column prop="name" label="姓名"/>
        <el-table-column prop="address" label="学院" />
        <el-table-column prop="operation" label="操作" >
          <template #default="scope">
            <el-button type="warning" plain>查看</el-button>
            <el-button type="success" plain>同意</el-button>
            <el-button type="danger" plain>拒绝</el-button>
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
import { ref, reactive } from 'vue'
// 表单选择
const multipleSelection = ref('')
const handleSelectionChange = val => {
  multipleSelection.value = val
  // console.log(multipleSelection.value)
}
const changePage = reactive({
  currentPage:1, //默认当前页面为1
  pageSize:14,
  total:20, //总共有多少数据
  pageSizes: [10, 14]
})
//这里是获取当前页数
const handleCurrentChange = (val)=> {
  changePage.currentPage = val
}
const tableData = [

]
</script>

