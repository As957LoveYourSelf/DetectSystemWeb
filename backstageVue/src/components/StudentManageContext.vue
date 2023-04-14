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
      <el-button class="ml-8" type="primary" :icon="CirclePlus" @click="dialogFormVisible = true" round>添加</el-button>
      <!-- 添加学生对话框 -->
      <el-dialog v-model="dialogFormVisible" title="添加学生信息" width="380px">
        <el-form :model="addStuData" :rules="rules" ref="form">
          <el-form-item prop="uname" label="学生名称" label-width="120px">
            <el-input type="text" v-model="addStuData.uname" autocomplete="off" clearable="true" />
          </el-form-item>
          <el-form-item prop="uno" label="学生学号" label-width="120px">
            <el-input v-model="addStuData.uno" autocomplete="off" clearable="true" />
          </el-form-item>
          <el-form-item prop="college" label="学生所在学院" label-width="120px">
            <el-select @change="getMajor" size="large" v-model="addStuData.college" placeholder="请选择学院">
              <el-option
                  v-for="item in college_ops"
                  :key="item"
                  :value="item"
                  :label="item"
              />
            </el-select>
          </el-form-item>
          <el-form-item label-width="50">
            <el-radio-group v-model="addStuData.sex">
              <el-radio  label="男" size="large">男</el-radio>
              <el-radio label="女" size="large">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item prop="major" label="学生所在专业" label-width="120px">
            <el-select @change="searchClass" size="large" v-model="addStuData.major" placeholder="请选择专业">
              <el-option
                  v-for="item in major_ops"
                  :key="item"
                  :value="item"
                  :label="item"
              />
            </el-select>
          </el-form-item>
          <el-form-item prop="grade" label="学生年级" label-width="120px">
            <el-select @change="searchClass" size="large" v-model="addStuData.grade" placeholder="请选择年级">
              <el-option
                  v-for="item in grade_ops"
                  :key="item"
                  :value="item"
                  :label="item"
              />
            </el-select>
          </el-form-item>
          <el-form-item prop="classname" label="学生所在班级" label-width="120px">
            <el-select size="large" v-model="addStuData.classname" placeholder="请选择班级">
              <el-option
                  v-for="item in classname"
                  :key="item"
                  :value="item"
                  :label="item"
              />
            </el-select>
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
    </div>
    <!-- 信息栏 -->
    <div style="min-height:85%; padding: 10px ">
      <el-table
          :data="tableData.slice((changePage.currentPage -1) * changePage.pageSize, changePage.currentPage*changePage.pageSize)"
          height="700"
          stripe border>
        <el-table-column prop="class" label="所属班级" />
        <el-table-column prop="sname" label="姓名"/>
        <el-table-column prop="sno" label="学号"/>
        <el-table-column prop="major" label="专业"/>
        <el-table-column prop="college" label="学院" />
        <el-table-column prop="operation" width="430" label="操作" >
          <template #default="scope">
            <el-button @click="removeStudent(scope.row.sno)" type="danger" plain>移除</el-button>
            <el-button @click="resetpsd(scope.row.sno)" type="warning" plain>重置密码</el-button>
            <el-button @click="resetFI(scope.row.sno)" type="primary" plain>重置人脸导入次数</el-button>
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

<script>
import {addStudent, removeStudent, selectStudent} from "../utils/studentManage";
import {useRouter} from "vue-router";
import {
  Delete,
  Search,
  CirclePlus
} from '@element-plus/icons-vue'
import {resetFI, resetPsd} from "../utils/user";
import {getCollegeSelector, getGradeSelector, getMajorSelector} from "../utils/courseManage";
import {searchCls} from "../utils/classManage";
export default {
  data(){
    return{
      tableData: [],
      searchData:{sno:'', sname:''},
      classname:[],
      unoInput: '',
      unameInput: '',
      changePage:{
        currentPage:1, //默认当前页面为1
        pageSize:14,
        total: 0, //总共有多少数据
        pageSizes: [10, 14]
      },
      addStuData:{sex:"女",uno:"",uname:"",college:null,grade:null,major:'',classname:null},
      searchCls:{college:null, major:null, grade:null},
      college_ops:[],
      grade_ops:[],
      dialogFormVisible:false,
      major_ops:[],
      Delete:Delete,
      Search:Search,
      CirclePlus:CirclePlus,
      router:useRouter(),
      rules:{
        uname:{ required: true, message: '请输入名称', trigger: 'blur' },
        uno:{ required: true, message: '请输入学号', trigger: 'blur' },
        college:{ required: true, message: '请选择学院', trigger: 'blur' },
        major:{ required: true, message: '请选择专业', trigger: 'blur' },
        grade:{ required: true, message: '请选择年级', trigger: 'blur' },
        classname:{ required: true, message: '请选择班级', trigger: 'blur' }
      }
    }
  },
  methods:{
    //这里是获取当前页数
    handleCurrentChange(val){
      this.changePage.currentPage = val
    },
    resetDialog(){
      this.addStuData ={sex:"女",uno:"",uname:"",college:null,grade:null,major:'',classname:null}
      this.grade_ops = null
      this.major_ops = null
      this.classname = null
    },
    removeStudent(sno){
      const postData = {sno:sno}
      removeStudent(postData).then(res =>{
        if (res.data === 'success'){
          this.$message({
            type:'success',
            message:'已删除用户'+sno
          })
        }else {
          this.$message({
            type:'error',
            message:'用户'+sno+'删除失败'
          })
        }
      }).catch(err => {
        console.log(err)
      })
    },
    resetpsd(sno){
      const postData = {uid:sno}
      resetPsd(postData).then(res =>{
        if (res.data === "success"){
          this.$message({
            type:'success',
            message:'已重置用户'+sno+'的密码'
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
    // 搜索函数
    searchfn() {
      this.searchData.sno = this.unoInput
      this.searchData.sname = this.unameInput
      let loading = this.$loading({
        lock:true,
        text:"查询中"
      })
      // console.log(this.searchData)
      selectStudent(this.searchData).then(res => {
        // console.log(res.data)
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
    reset(){
      this.unoInput = ''
      this.unameInput = ''
    },
    // 查看学生详情
    getDetail(sno) {
      this.router.push({
        name:"StudentDetail",
        query:{sno:sno}
      })
    },
    //
    getMajor(){
      this.addStuData.major = ''
      this.searchClass()
      const params = {college:this.addStuData.college}
      getMajorSelector(params).then(res=>{
        this.major_ops = res.data
      })
    },
    add(){
      this.$refs.form.validate(valid => {
        if (valid){
          console.log(this.addStuData)
          addStudent(this.addStuData).then(res => {
            if (res.data == "success"){
              this.$message({
                type:'success',
                message:'添加成功'
              })
            }else if (res.data == "exist") {
              this.$message({
                type:'error',
                message:'用户已存在！'
              })
            }
          })
        }
      })
    },
    searchClass(){
      this.searchCls.college = this.addStuData.college
      this.searchCls.grade = this.addStuData.grade
      this.searchCls.major = this.addStuData.major
      // console.log(this.searchCls)
      searchCls(this.searchCls).then(res => {
        this.classname = res.data
        this.addStuData.classname = this.classname[0]
      }).catch(err => {
        console.log(err)
      })
    }
  },
  created() {
    getCollegeSelector().then(res => {
      this.college_ops = res.data
    })
    getGradeSelector().then(res => {
      this.grade_ops = res.data
    })
  }
}
</script>

