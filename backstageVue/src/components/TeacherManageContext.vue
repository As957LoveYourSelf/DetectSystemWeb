<template>
  <!-- 主体内容 -->
  <div style="flex: 1;">
    <!-- 搜索栏 -->
    <div style="padding: 10px;">
      <!-- 搜索框 -->
      <el-select class="w-300" size="large" v-model="college" placeholder="请选择教师所在学院">
        <el-option
            v-for="item in college_ops"
            :key="item"
            :value="item"
            :label="item"
            :disabled="false"
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
      <el-table
          :data="tableData.slice((changePage.currentPage -1) * changePage.pageSize, changePage.currentPage*changePage.pageSize)"
          height="700"
          stripe border>

        <el-table-column prop="tno" label="教工号"/> <!-- tno -->
        <el-table-column prop="title" label="职位" /> <!-- title -->
        <el-table-column prop="tname" label="教师姓名"/> <!-- tname -->
        <el-table-column prop="college" label="所属学院" /> <!-- college -->
        <el-table-column prop="operation" label="操作" >
          <template #default="scope">
            <el-button @click="removeTeacher" type="danger" plain>移除</el-button>
            <el-button @click="resetpsd(scope.row.tno)" type="warning" plain>重置密码</el-button>
            <el-button @click="resetFI(scope.row.sno)" type="warning" plain>重置人脸导入次数</el-button>
            <el-button type="success" @click="getDetail(scope.row.tno)" plain>查看</el-button>
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

<script>
import {
  Delete,
  Search,
} from '@element-plus/icons-vue'
import {
  getCollegeSelector, removeTeacher,
  searchTeacherInfo,
} from "../utils/teacherManage";
import {useRouter} from 'vue-router'
import {resetFI} from "../utils/user";
export default {
  data(){
    return{
      unoInput:'',
      unameInput:'',
      tableData:[],
      college:'',
      college_ops:[],
      changePage:{
        currentPage:1, //默认当前页面为1
        pageSize:15,
        total: 0, //总共有多少数据
        pageSizes: [8, 15]
      },
      Delete:Delete,
      Search:Search,
      router:useRouter()
    }
  },
  methods:{
    //这里是获取当前页数
    handleCurrentChange(val){
      this.changePage.currentPage = val
    },
    removeTeacher(tno){
      const postdata = {tno:tno}
      removeTeacher(postdata).then(res =>{
        if (res.data === 'success'){
          this.$message({
            type:'success',
            message:'已删除用户'+tno
          })
        }else {
          this.$message({
            type: 'error',
            message: '用户' + tno + '删除失败'
          })
        }
      }).catch(err =>{
        console.log(err)
      })
    },
    resetpsd(tno){
      const postData = {uid:tno}
      resetPsd(postData).then(res =>{
        if (res.data === "success"){
          this.$message({
            type:'success',
            message:'已重置用户'+tno+'的密码'
          })
        }else {
          this.$message({
            type:'error',
            message:'重置失败'
          })
        }
      }).catch(err => {
        console.log(err)
      })
    },
    resetFI(sno){
      const postData = {uid:sno}
      resetFI(postData).then(res =>{
        if (res.data === "success"){
          this.$message({
            type:'success',
            message:'已重置用户'+sno+'的人脸录入次数'
          })
        }else {
          this.$message({
            type:'error',
            message:'重置失败'
          })
        }
      }).catch(err => {
        console.log(err)
      })
    },
    // 查询函数
    searchfn() {
      const searchData = {tno:this.unoInput, tname:this.unameInput, college:this.college}
      // console.log(this.searchData)
      let loading = this.$loading({
        lock:true,
        text:"查询中"
      })
      searchTeacherInfo(searchData).then(res => {
        if (res.data != null){
          this.tableData = res.data
          this.changePage.currentPage = 1
          this.changePage.total = this.tableData.length
          loading.close()
          this.$message({
            type:'success',
            message:'查询成功'
          })
        }else {
          loading.close()
          this.$message({
            type:'warning',
            message:'查找不到您要的数据'
          })
        }
      }).catch(err => {
        console.log(err)
        loading.close()
        this.$message({
          type:'error',
          message:'查询失败'
        })
      })
    },
    // 重置函数
    reset() {
      this.unoInput = ''
      this.unameInput = ''
      this.college=''
   },
    getDetail(tno){
      // 跳转至详情页面
      this.router.push({
        name:"TeacherDetail",
        query:{tno:tno}
      })
    }
  },
  created() {
    getCollegeSelector().then(res => {
      // console.log(res)
      this.college_ops = res.data
    }).catch(err => {
      console.log(err)
    })
  }
}
</script>

