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
        <el-table-column prop="sname" label="姓名"/>
        <el-table-column prop="sno" label="学号"/>
        <el-table-column prop="major" label="专业"/>
        <el-table-column prop="college" label="学院" />
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

<script>
import {getStudentDetail, selectStudent} from "../utils/studentManage";
import {useRouter} from "vue-router";
import {
  Delete,
  Search,
} from '@element-plus/icons-vue'
export default {
  data(){
    return{
      tableData: [],
      searchData:{sno:'', sname:''},
      unoInput: '',
      unameInput: '',
      changePage:{
        currentPage:1, //默认当前页面为1
        pageSize:14,
        total: 0, //总共有多少数据
        pageSizes: [10, 14]
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
    // 搜索函数
    searchfn() {
      this.searchData.sno = this.unoInput
      this.searchData.sname = this.unameInput
      let loading = this.$loading({
        lock:true,
        text:"查询中"
      })
      console.log(this.searchData)
      selectStudent(this.searchData).then(res => {
        // console.log(res.data)
        this.tableData = res.data
        this.changePage.total = this.tableData.length
        loading.close()
        this.$message({
          type:'success',
          message:'查询成功'
        })
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
    }
  }
}
</script>

