<template>
  <div style="flex: 1;">
    <div style="padding: 10px">
      <!-- 搜索框 -->
      <el-select @change="getMajor(false)" class="w-200" size="large" v-model="college" placeholder="请选择学院">
        <el-option
            v-for="item in college_ops"
            :key="item"
            :value="item"
            :label="item"
            :disabled=false
        />
      </el-select>
      <el-select class="w-200 ml-8" size="large" v-model="major" placeholder="请选择专业">
        <el-option
            v-for="item in major_ops"
            :key="item"
            :value="item"
            :label="item"
            :disabled=false
        />
      </el-select>
      <el-select class="w-180 ml-8" size="large" v-model="grade" placeholder="请选择年级">
        <el-option
            v-for="item in grade_ops"
            :key="item"
            :value="item"
            :label="item"
            :disabled=false
        />
      </el-select>
      <el-button class=" ml-8" type="success" :icon="Search" @click="searchfn" round>搜索</el-button>
      <el-button class="ml-8" type="warning" :icon="Refresh" @click="reset" round>重置</el-button>
      <el-button class="ml-8" type="primary" :icon="CirclePlus" @click="dialogFormVisible = true" round>添加课程</el-button>
      <!-- 添加课程对话框 -->
      <el-dialog v-model="dialogFormVisible" title="添加课程" width="350px">
        <el-form :model="addCourseData">
          <el-form-item label="课程所属学院" label-width="100px">
            <el-select @change="getMajor(true)" size="large" v-model="addCourseData.college" placeholder="请选择学院">
              <el-option
                  v-for="item in college_ops"
                  :key="item"
                  :value="item"
                  :label="item"
                  :disabled=false
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-radio-group v-model="addCourseData.ctype">
              <el-radio @click="addCourseData.major=''" label="public" size="large">公共课</el-radio>
              <el-radio label="major" size="large">专业课</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="课程所属专业" label-width="100px">
            <el-select size="large" v-model="addCourseData.major" placeholder="请选择专业">
              <el-option
                  v-for="item in major_ops1"
                  :key="item"
                  :value="item"
                  :label="item"
                  :disabled="addCourseData.ctype==='public'?true:false"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="课程开设年级" label-width="100px">
            <el-select size="large" v-model="addCourseData.grade" placeholder="请选择年级">
              <el-option
                  v-for="item in grade_ops"
                  :key="item"
                  :value="item"
                  :label="item"
                  :disabled=false
              />
            </el-select>
          </el-form-item>
          <el-form-item label="课程名称" label-width="100px">
            <el-input v-model="addCourseData.cname" autocomplete="off" />
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
      <div class="mb-2 flex items-center text-sm">
        <el-radio-group v-model="type" class="ml-8">
          <el-radio label="all" size="large">所有</el-radio>
          <el-radio label="public" size="large">公共课</el-radio>
          <el-radio label="major" size="large">专业课</el-radio>
        </el-radio-group>
      </div>

    </div>
    <!-- 信息栏 -->
    <div style=" padding: 10px;">
      <el-table
          :data="tableData.slice((changePage.currentPage -1) * changePage.pageSize, changePage.currentPage*changePage.pageSize)"
          height="700"
          stripe border>
        <el-table-column prop="cno" label="课程号" />
        <el-table-column prop="cname" label="课程名称" />
        <el-table-column prop="major" label="所属专业"/>
        <el-table-column prop="college" label="所属学院" />
        <el-table-column prop="type" label="课程类型" />
        <el-table-column prop="grade" label="开设年级" />
        <el-table-column prop="operation" label="操作" >
          <template #default="scope">
<!--            <el-button type="success" @click="" :icon="Search" plain>查看</el-button>-->
            <el-button type="danger" @click="delectCs(scope.row.cno, scope.row.type)" :icon="Delete" plain>删除</el-button>
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
  Refresh,
  CirclePlus
} from '@element-plus/icons-vue'
import {
  getCollegeSelector,
  getGradeSelector,
  selectCourse,
  importCourse,
  deleteCourse,
  getMajorSelector
} from '../utils/courseManage'
export default {
  data(){
    return{
      tableData:[],
      changePage:{
        currentPage:1, //默认当前页面为1
        pageSize:14,
        total: 0, //总共有多少数据
        pageSizes: [10, 14],
      },
      college:'',
      grade:'',
      major:'',

      type:'all',
      major_ops:[],
      college_ops:[],
      grade_ops:[],
      addCourseData:{college:null,grade:null, major:null,cname:null,ctype:null},
      major_ops1:[],

      dialogFormVisible:false,
      Delete:Delete,
      Search:Search,
      Refresh:Refresh,
      CirclePlus:CirclePlus
    }
  },
  methods:{
    handleCurrentChange(val){
      this.changePage.currentPage = val
    },
    add(){
      let t = true
      if (this.addCourseData.ctype!=null&&this.addCourseData.ctype==='public'){
        this.addCourseData.major = ''
      }
      console.log(this.addCourseData)
      // 判断输入是否符合规范
      for (let key in this.addCourseData) {
        if (this.addCourseData[key]== null) {
          this.$messageBox({
            type:'warning',
            message:'请确保正确输入课程信息'
          })
          t = false
          break
        }
      }
      if (t){
        let loading = this.$loading({
          lock:true,
          text:"添加中"
        })
        importCourse(this.addCourseData).then(res => {
          if (res.data === 'success'){
            this.dialogFormVisible = false
            loading.close()
            this.$message({
              type:'success',
              message:'添加成功'
            })
          }else {
            loading.close()
            this.$message({
              type:'error',
              message:'添加失败'
            })
          }
        })
      }
    },
    getMajor(isDialog){
      if (isDialog){
        const params = {college:this.addCourseData.college}
        getMajorSelector(params).then(res=>{
          this.major_ops1 = res.data
        })
      }else {
        this.major = ""
        const params = {college:this.college}
        getMajorSelector(params).then(res=>{
          this.major_ops = res.data
        })
      }
    },
    delectCs(cno, type){
      const postParams={cno:cno, courseType:type}
      deleteCourse(postParams).then(res =>{
        if (res.data === 'success'){
          this.$message({
            type:'success',
            message:'删除成功!'
          })
        }else {
          this.$message({
            type:'error',
            message:'删除失败!'
          })
        }
      }).catch(err =>{
        this.$message({
          type:'error',
          message:'未知错误!'
        })
      })
    },
    searchfn(){
      const postParams={college:this.college, major:this.major, grade:this.grade === ''?null:this.grade, type:this.type}
      // console.log(postParams)
      let loading = this.$loading({
        lock:true,
        text:"查询中"
      })
      selectCourse(postParams).then(res => {
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
      }).catch(err =>{
        console.log(err)
        loading.close()
        this.$message({
          type:'error',
          message:'查询失败'
        })
      })
    },
    reset(){
      this.major_ops = []
      this.college = ''
      this.grade = ''
      this.major = ''
      this.type = 'all'
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
