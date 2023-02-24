<template>
  <!-- 主体内容 -->
  <div style="flex: 1;">
    <!-- 搜索栏 -->
    <div style="padding: 10px;">
      <!-- 搜索框 -->
      <el-select class="w-300" size="large" v-model="classroom_building" @change="change" placeholder="请选择教学大楼">
        <el-option
            v-for="item in classroom_building_ops"
            :key="item"
            :value="item"
            :label="item"
            :disabled="false"
        />
      </el-select>
      <el-select class="w-300 ml-8" size="large" v-model="classroom_floor" placeholder="请选择教室楼层">
        <el-option
            v-for="item in classroom_floor_ops"
            :key="item"
            :value="item"
            :label="item"
            :disabled="false"
        />
      </el-select>
      <el-radio-group v-model="OrderSelect" class="ml-8" >
        <el-radio-button label="0" border>未预约</el-radio-button>
        <el-radio-button label="1" border>已预约</el-radio-button>
      </el-radio-group>
      <!-- 按钮 -->
      <el-button class="ml-8" type="success" :icon="Search" @click="searchFn" round>搜索</el-button>
      <el-button class="ml-8" type="warning" :icon="Delete" @click="reset" round>重置</el-button>
    </div>
    <!-- 信息栏 -->
    <div style="min-height:87%; padding: 10px ">
      <el-table :data="tableData.slice((changePage.currentPage -1) * changePage.pageSize, changePage.currentPage*changePage.pageSize)" stripe border>
        <el-table-column prop="building" label="所属教学楼" />
        <el-table-column prop="roomNo" label="课室编号"/>
        <el-table-column prop="isOrder" label="是否被预约" >
          <template #default="scope">
            <el-tag
                :type="scope.row.isOrder === 'NO' ? '' : 'success'"
                disable-transitions
            >{{ scope.row.isOrder }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="operation" label="操作" >
          <template #default="scope">
            <el-button type="success" @click="order(scope.row.roomNo)" :disabled="scope.row.isOrder === 'YES'" plain>预约</el-button>
            <el-button type="danger" @click="deorder(scope.row.roomNo)" :disabled="scope.row.isOrder === 'NO'" plain>取消预约申请</el-button>
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
import { ElMessage, ElMessageBox } from 'element-plus'
import {getBuildingFloorSelector, getBuildingSelector, orderClassroom,deorderClassroom,getClassroomInfo} from "../utils/classroomManage";
export default {
  data(){
    return{
      OrderSelect:"0",
      postData:{
        buildingName:'',
        isOrder:'',
        floor:''
      },
      selectorData:{buildingName:''},
      orderData:{clsNo:''},
      changePage:{
        currentPage:1, //默认当前页面为1
        pageSize:14,
        total:0, //总共有多少数据
        pageSizes: [10, 14]
      },
      classroom_building:'',
      classroom_building_ops:[],
      classroom_floor:null,
      classroom_floor_ops:[],
      tableData:[],
      Delete:Delete,
      Search:Search,
    }
  },
  methods:{
    //这里是获取当前页数
    handleCurrentChange(val){
      this.changePage.currentPage = val
    },
    // 查看课室使用详情
    searchFn(){
      this.postData.buildingName = this.classroom_building
      this.postData.isOrder = Number.parseInt(this.OrderSelect)
      this.postData.floor = this.classroom_floor
      let loading = this.$loading({
        lock:true,
        text:"查询中"
      })
      console.log(this.postData)
      getClassroomInfo(this.postData).then(res => {
        this.tableData = res.data
        this.changePage.total = this.tableData.length
        // console.log(res.data)
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
          message:'查询成功'
        })
      })
    },
    //重置选框
    reset(){
      this.classroom_building = ''
      this.classroom_floor = null
    },
    //预约
    order(no){
      this.orderData.clsNo = no
      orderClassroom(this.orderData).then(res => {
        if (res.data === 'success'){
          ElMessageBox.alert('预约成功', '消息', {
            confirmButtonText: 'OK'
          })
        }else {

        }
      }).catch(err =>{
        console.log(err)
      })
    },
    //预约申请
    deorder(no){
      this.orderData.clsNo = no
      deorderClassroom(this.orderData).then(res => {
        if (res.data === 'success'){
          ElMessageBox.alert('已退回预约申请', '消息', {
            confirmButtonText: 'OK'
          })
        }else {

        }
      }).catch(err =>{
        console.log(err)
      })
    },
    change(){
      this.selectorData.buildingName = this.classroom_building
      getBuildingFloorSelector(this.selectorData).then(res => {
        // console.log(res.data)
        this.classroom_floor_ops = res.data
      }).catch(err =>{
        console.log(err)
      })
    }
  },
  created() {
    getBuildingSelector().then(res => {
      this.classroom_building_ops = res.data
    }).catch(err =>{
      console.log(err)
    })
  }
}
</script>

