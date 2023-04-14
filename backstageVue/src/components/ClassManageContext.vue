<template>
  <!-- 主体内容 -->
  <div style="flex: 1;">
    <!-- 搜索栏 -->
    <div style="padding: 10px;">
      <!-- 搜索框 -->
      <el-select class="w-300" size="large" v-model="college" placeholder="请选择学院">
        <el-option
            v-for="item in college_ops"
            :key="item"
            :value="item"
            :label="item"
            :disabled=false
        />
      </el-select>
      <el-select class="w-300 ml-8" size="large" v-model="grade" placeholder="请选择年级">
        <el-option
            v-for="item in grade_ops"
            :key="item"
            :value="item"
            :label="item"
            :disabled=false
        />
      </el-select>
      <!-- 按钮 -->
      <el-button class="ml-8" type="success" :icon="Search" @click="searchfn" round>搜索</el-button>
      <el-button class="ml-8" type="warning" :icon="Delete" @click="reset" round>重置</el-button>
      <el-button class="ml-8" type="primary" :icon="CirclePlus" @click="dialogFormVisible = true" round>添加班级</el-button>
      <!-- 班级添加 -->
      <el-dialog v-model="dialogFormVisible" title="添加课程" width="350px">
        <el-form :model="addClassData" :rules="rules" ref="form">
          <el-form-item label="班级名称" label-width="100px">
            <el-input v-model="addClassData.classname" autocomplete="off" disabled="true"/>
          </el-form-item>
          <el-form-item prop="college" label="课程所属学院" label-width="100px">
            <el-select @change="getMajor" size="large" v-model="addClassData.college" placeholder="请选择学院">
              <el-option
                  v-for="item in college_ops"
                  :key="item"
                  :value="item"
                  :label="item"
                  :disabled=false
              />
            </el-select>
          </el-form-item>
          <el-form-item prop="grade" label="班级所属年级" label-width="100px">
            <el-select size="large" @change="setclsname" v-model="addClassData.grade" placeholder="请选择年级">
              <el-option
                  v-for="item in grade_ops"
                  :key="item"
                  :value="item"
                  :label="item"
              />
            </el-select>
          </el-form-item>
          <el-form-item prop="major" label="课程所属专业" label-width="100px">
            <el-select @change="setClassname" size="large" v-model="addClassData.major" placeholder="请选择专业">
              <el-option
                  v-for="item in major_ops"
                  :key="item"
                  :value="item"
                  :label="item"
              />
            </el-select>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取消</el-button>
            <el-button type="primary" @click="add">
              提交
            </el-button>
          </span>
        </template>
      </el-dialog>
    </div>
    <!-- 信息栏 -->
    <div style="min-height:85%; padding: 10px ">
      <el-table :data="tableData.slice((changePage.currentPage -1) * changePage.pageSize, changePage.currentPage*changePage.pageSize)"
                height="700"
                stripe border>
        <el-table-column prop="college" label="学院" />
        <el-table-column prop="major" label="专业"/>
        <el-table-column prop="grade" label="年级" />
        <el-table-column prop="class_name" label="班级名称" />
        <el-table-column prop="headmaster" label="班主任" />
        <el-table-column prop="operation" label="操作" >
          <template #default="scope">
            <el-button type="success" @click="getClassDetailInfo(scope.row.class_name)" plain>查看</el-button>
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
  addCls,
  getClassNo,
  getCollegeSelector,
  getGradeSelector,
  selectClass,
} from "../utils/classManage"
import {useRouter} from "vue-router";
import {getMajorSelector} from "../utils/courseManage";
export default {
  data(){
    return{
      college:'',
      college_ops:[],
      grade:null,
      grade_ops:[],
      tableData:[],
      major_ops:[],
      classNo:null,
      addClassData:{classname:null, college:null, grade:null, major:null, headmaster:null},
      getClassNoData:{college:null, grade:null, major:null},
      changePage:{
        currentPage:1, //默认当前页面为1
        pageSize:14, //一页多少数据
        total: 0, //总共有多少数据
        pageSizes: [5, 10, 14] //一页可供显示多少数据
      },
      rules:{
        college:{ required: true, message: '请选择学院', trigger: 'blur' },
        major:{ required: true, message: '请选择专业', trigger: 'blur' },
        grade:{ required: true, message: '请选择年级', trigger: 'blur' },
      },
      dialogFormVisible:false,
      Search:Search,
      Delete:Delete,
      CirclePlus:CirclePlus,
      router:useRouter()
    }
  },
  methods:{
    add(){
      this.$refs.form.validate(valid => {
        if (valid){
          console.log(this.addClassData)
          addCls(this.addClassData).then(res => {
            if (res.data == "success"){
              this.$message({
                type:'success',
                message:'添加成功'
              })
            }else{
              this.$message({
                type:'error',
                message:'添加失败'
              })
            }
          })
        }
      })
    },
    setclsname(){
      if (this.addClassData.college != null && this.addClassData.major != null && this.addClassData.major !== ''){
        this.setClassname()
      }
    },
    getMajor(){
      this.addClassData.major = ''
      this.addClassData.classname = ''
      const params = {college:this.addClassData.college}
      getMajorSelector(params).then(res=>{
        this.major_ops = res.data
      })
    },
    setClassname(){
      this.getClassNoData.college = this.addClassData.college
      this.getClassNoData.grade = this.addClassData.grade
      this.getClassNoData.major = this.addClassData.major
      getClassNo(this.getClassNoData).then(res => {
        this.addClassData.classname = this.addClassData.grade+this.addClassData.major+res.data+"班"
      }).catch(err => {
        console.log(err)
      })
    },
    searchfn(){
      const params= {grade:this.grade, college:this.college}
      // console.log(params)
      let loading = this.$loading({
        lock:true,
        text:"查询中"
      })
      selectClass(params)
        .then(res => {
          // console.log(res.data)
          if (res.data == null){
            this.tableData = []
            loading.close()
            this.$message({
              type:'warning',
              message:'查找不到您要的数据'
            })
          }else {
            this.tableData = res.data
            this.changePage.currentPage = 1
            this.changePage.total = this.tableData.length
            loading.close()
            this.$message({
              type:'success',
              message:'查询成功'
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
    // 查看详情
    getClassDetailInfo(classname){
      this.router.push({
        name: 'ClassDetail',
        query: {classname: classname}
      })
    },
    // 重置函数
    reset() {
      console.log("reset")
      this.tableData = []
      this.college = ''
      this.grade = null
    },
    handleCurrentChange(val){
      // 分页查询
      this.changePage.currentPage = val
      // this.searchfn(val, this.changePage.pageSize)
    },
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

