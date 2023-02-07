<template>
  <div style="flex: 1;">
    <div style="padding: 10px; height: 100px">
      <el-upload
          ref="upload"
          class="upload-demo"
          action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
          :limit="1"
          :on-exceed="handleExceed"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
          :before-upload="beforeUpload"
          accept=".txt,.csv"
          :auto-upload="false"
      >
        <template #trigger>
          <el-button type="primary">选择课程数据文件</el-button>
        </template>
        <el-button class="ml-8" type="success" @click="submitUpload">
          上传课程数据文件
        </el-button>
        <template #tip>
          <div class="el-upload__tip text-red ml-8">
            仅支持上传一个文件，文件格式为txt或者csv
          </div>
        </template>
      </el-upload>
    </div>
    <!-- 信息栏 -->
    <div style=" min-height: 77%; padding: 10px; margin-top: 10px ">
      <el-table :data="tableData.slice((changePage.currentPage -1) * changePage.pageSize, changePage.currentPage*changePage.pageSize)" stripe border>
        <el-table-column prop="cname" label="课程名称" />
        <el-table-column prop="major" label="所属专业"/>
        <el-table-column prop="collage" label="所属学院" />
        <el-table-column prop="operation" label="操作" >
          <template #default="scope">
            <el-button type="success" @click="" plain>查看</el-button>
            <el-button type="danger" @click="" plain>删除</el-button>
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
import {ElMessage, ElMessageBox, genFileId} from 'element-plus'
import { ref, reactive } from 'vue'

const upload = ref()

const handleExceed = (files) => {
  upload.value.clearFiles()
  const file = files[0]
  file.uid = genFileId()
  upload.value.handleStart(file)
}
const handleRemove= (file, uploadFiles) => {
  console.log(file, uploadFiles)
}

const handlePreview = (uploadFile) => {
  console.log(uploadFile)
}

const beforeRemove= (uploadFile) => {
  return ElMessageBox.confirm(
      `Cancel the transfert of ${uploadFile.name} ?`
  ).then(
      () => true,
      () => false
  )
}

const beforeUpload = (rawFile) => {
  if (rawFile.type !== 'txt/csv') {
    ElMessage.error('文件必须为TXT或者CSV格式！')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('文件大小不超过2MB！')
    return false
  }
  return true
}

const submitUpload = () => {
  upload.value.submit()
}

let tableData = [

]

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


</script>
