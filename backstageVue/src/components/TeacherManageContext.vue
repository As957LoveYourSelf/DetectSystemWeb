<template>
  <!-- 主体内容 -->
  <div style="flex: 1;">
    <!-- 搜索栏 -->
    <div style="padding: 10px;">
      <!-- 搜索框 -->
      <el-select class="w-300" size="large" v-model="collage" placeholder="请选择教师所在学院">
        <el-option
            v-for="item in collage_ops"
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
      <el-table :data="tableData.slice((changePage.currentPage -1) * changePage.pageSize, changePage.currentPage*changePage.pageSize)" stripe border>

        <el-table-column prop="tno" label="教工号"/> <!-- tno -->
        <el-table-column prop="title" label="职位" /> <!-- title -->
        <el-table-column prop="tname" label="教师姓名"/> <!-- tname -->
        <el-table-column prop="collage" label="所属学院" /> <!-- collage -->
        <el-table-column prop="teachCourse" label="所教课程" /> <!-- TeachMajorCourseMapper -->
        <el-table-column prop="operation" label="操作" >
          <template #default="scope">
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
  getCollageSelector,
  searchTeacherInfo,
  getTeacherDetail,
} from "../utils/teacherManage";
import {useRouter} from 'vue-router'
export default {
  data(){
    return{
      unoInput:'',
      unameInput:'',
      tableData:[],
      collage:'',
      collage_ops:[],
      detailData:{tno:''},
      searchData:{tno:'', tname:'', collage:''},
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
    // 查询函数
    searchfn() {
      this.searchData.tname = this.unameInput
      this.searchData.tno = this.unoInput
      this.searchData.collage = this.collage
      console.log(this.searchData)
      let loading = this.$loading({
        lock:true,
        text:"查询中"
      })
      searchTeacherInfo(this.searchData).then(res => {
        this.tableData = res.data
        this.changePage.total = this.tableData.length
        loading.close()
      }).catch(err => {
        console.log(err)
        loading.close()
      })
    },
    // 重置函数
    reset() {
      this.unoInput = ''
      this.unameInput = ''
      this.collage=''
   },
    getDetail(tno){
      this.detailData.tno = tno
      getTeacherDetail(this.detailData).then(res => {
        // console.log(res)
        // 跳转至详情页面
        this.router.push({
          name:"TeacherDetail",
          query:{info:JSON.stringify(res.data)}
        })
      }).catch(err => {
        console.log(err)
      })
    }
  },
  created() {
    getCollageSelector().then(res => {
      // console.log(res)
      this.collage_ops = res.data
    }).catch(err => {
      console.log(err)
    })
  }
}
</script>

