<template>
  <!-- 主体内容 -->
  <div style="flex: 1;">
    <!-- 搜索栏 -->
    <div style="padding: 10px;">
      <!-- 搜索框 -->
      <el-select class="w-300" size="large" v-model="collage" placeholder="请选择学院">
        <el-option
            v-for="item in collage_ops"
            :key="item"
            :value="item"
            :label="item"
            :disabled=false
        />
      </el-select>
      <el-select class="w-300 ml-8" size="large" v-model="level" placeholder="请选择年级">
        <el-option
            v-for="item in level_ops"
            :key="item"
            :value="item"
            :label="item"
            :disabled=false
        />
      </el-select>
      <!-- 按钮 -->
      <el-button class="ml-8" type="success" :icon="Search" @click="searchfn" round>搜索</el-button>
      <el-button class="ml-8" type="warning" :icon="Delete" @click="reset" round>重置</el-button>
    </div>
    <!-- 信息栏 -->
    <div style="min-height:87%; padding: 10px ">
      <el-table :data="tableData.slice((changePage.currentPage -1) * changePage.pageSize, changePage.currentPage*changePage.pageSize)" stripe border>
        <el-table-column prop="collage" label="学院" />
        <el-table-column prop="major" label="专业"/>
        <el-table-column prop="grade" label="年级" />
        <el-table-column prop="class_name" label="班级名称" />
        <el-table-column prop="headmaster" label="班主任" />
        <el-table-column prop="operation" label="操作" >
          <template #default="scope">
            <el-button type="success" @click="getClassDetailInfo(scope.row.classname)" plain>查看</el-button>
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
  getGradeSelector,
  selectClass,
  getClassDetail
} from "../utils/classManage"
import {useRouter} from "vue-router";

export default {
  data(){
    return{
      params:{grade:'',collage:''},
      data:{className:''},
      collage:'',
      collage_ops:[],
      level:'',
      level_ops:[],
      tableData:[],
      changePage:{
        currentPage:1, //默认当前页面为1
        pageSize:14, //一页多少数据
        total: 0, //总共有多少数据
        pageSizes: [10, 14] //一页可供显示多少数据
      },
      Search:Search,
      Delete:Delete,
      router:useRouter()
    }
  },
  methods:{
    searchfn(){
      this.params.grade = this.level
      this.params.collage = this.collage
      console.log(this.params)
      selectClass(this.params)
        .then(res => {
          if (res.data == null){
            this.tableData = []
            console.log("not data")
          }else {
            this.tableData = res.data
            this.changePage.total = this.tableData.length
          }
          console.log(res)
        })
        .catch(err => {
          console.log(err)
        })
    },
    // 查看详情
    getClassDetailInfo(classname){
      this.data.className = classname
      getClassDetail(this.data).then(res => {
        console.log(res.data)
        this.router.push({
          name: 'ClassDetail',
          params: {userdata: res.data}
        })
        console.log(res.data)
      }).catch(err => {
        console.log(err)
      })
    },
    // 重置函数
    reset() {
      console.log("reset")
      this.tableData = []
      this.collage = ''
      this.level = ''
    },
    //这里是获取当前页数
    handleCurrentChange(val){
      this.changePage.currentPage = val
    }
  },
  created() {
    getCollageSelector().then(res => {
      this.collage_ops = res.data
    })
    getGradeSelector().then(res => {
      this.level_ops = res.data
    })
  }
}
</script>

