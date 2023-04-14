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
      <el-button class="ml-8" type="primary" :icon="CirclePlus" @click="dialogFormVisible = true" round>添加</el-button>
    </div>
    <!-- 添加教师对话框 -->
    <el-dialog v-model="dialogFormVisible" title="添加教师信息" width="380px">
      <el-form :model="addTeaData" :rules="rules" ref="form">
        <el-form-item prop="uname" label="教师名称" label-width="120px">
          <el-input type="text" v-model="addTeaData.uname" autocomplete="off" clearable="true" />
        </el-form-item>
        <el-form-item prop="uno" label="教师工号" label-width="120px">
          <el-input v-model="addTeaData.uno" autocomplete="off" clearable="true" />
        </el-form-item>
        <el-form-item label-width="50">
          <el-radio-group v-model="addTeaData.title">
            <el-radio  label="讲师" size="large">讲师</el-radio>
            <el-radio label="教授" size="large">教授</el-radio>
            <el-radio label="副教授" size="large">副教授</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="college" label="教师所在学院" label-width="120px">
          <el-select size="large" v-model="addTeaData.college" placeholder="请选择学院">
            <el-option
                v-for="item in college_ops"
                :key="item"
                :value="item"
                :label="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label-width="50">
          <el-radio-group v-model="addTeaData.sex">
            <el-radio  label="男" size="large">男</el-radio>
            <el-radio label="女" size="large">女</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogFormVisible = false" type="danger">取消</el-button>
            <el-button @click="resetDialog" type="warning">重置</el-button>
            <el-button type="primary" @click="add">
              提交
            </el-button>
          </span>
      </template>
    </el-dialog>
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
        <el-table-column prop="operation" label="操作" width="430">
          <template #default="scope">
            <el-button @click="removeTeacher" type="danger" plain>移除</el-button>
            <el-button @click="resetpsd(scope.row.tno)" type="warning" plain>重置密码</el-button>
            <el-button @click="resetFI(scope.row.sno)" type="primary" plain>重置人脸导入次数</el-button>
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
  CirclePlus
} from '@element-plus/icons-vue'
import {
  addTeacher,
  getCollegeSelector, removeTeacher,
  searchTeacherInfo,
} from "../utils/teacherManage";
import {useRouter} from 'vue-router'
import {resetFI, resetPsd} from "../utils/user";
export default {
  data(){
    return{
      dialogFormVisible:false,
      unoInput:'',
      unameInput:'',
      tableData:[],
      college:'',
      college_ops:[],
      addTeaData:{uno:null, uname:null, sex:"男", college:null, title:"讲师"},
      changePage:{
        currentPage:1, //默认当前页面为1
        pageSize:15,
        total: 0, //总共有多少数据
        pageSizes: [8, 15]
      },
      rules:{
        uname:{ required: true, message: '请输入名称', trigger: 'blur' },
        uno:{ required: true, message: '请输入学号', trigger: 'blur' },
        college:{ required: true, message: '请选择学院', trigger: 'blur' },
        title:{ required: true, message: '请选择职称', trigger: 'blur' },
      },
      Delete:Delete,
      Search:Search,
      CirclePlus:CirclePlus,
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
    add(){
      this.$refs.form.validate(valid => {
        if (valid){
          addTeacher(this.addTeaData).then(res => {
            if (res.data == "success"){
              this.$message({
                type:'success',
                message:'添加成功'
              })
            }else if (res.data == "exist"){
              this.$message({
                type:'error',
                message:'用户已存在！'
              })
            }
          })
        }
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
    resetDialog(){
      this.addTeaData = {uno:null, uname:null, sex:"男", college:null, title:"讲师"}

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

