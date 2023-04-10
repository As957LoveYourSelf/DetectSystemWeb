<template>
  <el-container direction="vertical">
    <el-header height="170px">
      <!-- 搜索栏 -->
        <!-- 搜索框 -->
      <div>
        <el-select style="padding: 6px" class="w-300" size="large" v-model="classroom_building" @change="change" placeholder="请选择教学大楼">
          <el-option
              v-for="item in classroom_building_ops"
              :key="item"
              :value="item"
              :label="item"
              :disabled="false"
          />
        </el-select>
        <el-select style="padding: 6px" class="w-300" size="large" v-model="classroom_floor" placeholder="请选择教室楼层">
          <el-option
              v-for="item in classroom_floor_ops"
              :key="item"
              :value="item"
              :label="item"
              :disabled="false"
          />
        </el-select>
        <div style="padding: 6px">
          <el-radio-group v-model="OrderSelect">
            <el-radio-button label="2" border>所有</el-radio-button>
            <el-radio-button label="0" border>未预约</el-radio-button>
            <el-radio-button label="1" border>已预约</el-radio-button>
          </el-radio-group>
        </div>
        <!-- 按钮 -->
        <div style="padding: 6px">
          <el-button type="success" :icon="Search" @click="searchFn" round>搜索</el-button>
          <el-button type="warning" :icon="Delete" @click="reset" round>重置</el-button>
        </div>
      </div>
    </el-header>
    <el-main>
      <!-- 信息栏 -->
      <div >
        <el-table
            :data="tableData.slice((changePage.currentPage -1) * changePage.pageSize, changePage.currentPage*changePage.pageSize)"
            height="700"
            stripe border>
          <el-table-column prop="building" label="所属教学楼" />
          <el-table-column prop="roomNo" label="课室编号"/>
          <el-table-column prop="isOrder" label="是否已预约" >
            <template #default="scope">
              <el-tag
                  :type="scope.row.isOrder === 'NO' ? 'danger' : 'success'"
                  disable-transitions
              >{{ scope.row.isOrder }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="operation" label="操作" width="180px" >
            <template #default="scope">
              <el-button type="success" @click="openOrderDialog(scope.row.roomNo)" :disabled="scope.row.isOrder==='YES'" plain>预约</el-button>
              <el-button type="primary" @click="getDetail(scope.row.roomNo)" plain>查看</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 预约对话框 -->
        <el-dialog v-model="orderDialog" title="填写预约信息" width="350px">
          <el-form :model="orderData">
            <!-- 日期 -->
            <el-form-item label="日期" label-width="100px">
              <el-date-picker
                  v-model="orderData.time"
                  type="date"
                  placeholder="请选择日期"
              />
            </el-form-item>
          </el-form>
          <template #footer>
          <span class="dialog-footer">
            <el-button @click="orderDialog=false">取消</el-button>
            <el-button type="primary" @click="order">确认预约</el-button>
          </span>
          </template>
        </el-dialog>
        <!-- 查看预约信息对话框 -->
        <el-dialog v-model="detailDialog" title="预约信息" width="200px">
          <el-table :data="detailInfo">
            <el-table-column property="clsNo" label="预约课室名称"/>
            <el-table-column property="time" label="使用时间"/>
            <el-table-column property="uno" label="预约人" />
            <el-table-column prop="operation" label="操作" >
              <template #default="scope">
                <el-button type="danger" @click="deorder(scope.row.clsNo)" :disabled="route.query.uid !== scope.row.uno" plain>取消预约</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-dialog>

        <!-- 分页栏 -->
        <div style="padding: 10px">
          <el-pagination
              v-model:current-page="changePage.currentPage"
              v-model:page-size="changePage.pageSize"
              v-model:page-sizes="changePage.pageSizes"
              @current-change="handleCurrentChange"
              @background="true"
              layout="prev, pager, next"
              :total="changePage.total"
          />
        </div>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import {
  Delete,
  Search,
} from '@element-plus/icons-vue'
import {ElMessageBox } from 'element-plus'
import {useRoute} from 'vue-router'
import axios from "axios";
export default {
  data(){
    return{
      OrderSelect:"2",
      postData:{
        buildingName:'',
        isOrder:'',
        floor:''
      },
      selectorData:{buildingName:''},
      orderData:{clsNo:'',time:'',uid:''},
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
      orderDialog:false,
      detailDialog:false,
      detailInfo:[],
      route:useRoute()
    }
  },
  methods:{
    //这里是获取当前页数
    handleCurrentChange(val){
      this.changePage.currentPage = val
    },
    //查看预约详情
    getDetail(csno){
      axios({
        method: "post",
        url: "/api/classroomManage/getOrderDetail",
        headers: {
          'Content-Type': 'application/json;charset:utf-8;',
          // 'Bearer ' +
          'Authorization': this.route.query.token
        },
        params: {clsNo:csno}
      }).then(res => {
        this.detailInfo = res.data.data
        this.detailDialog = true
      }).catch(err =>{
        console.log(err)
      })
    },
    openOrderDialog(clsno){
      this.orderDialog = true
      this.orderData.clsNo = clsno
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
      axios({
        method: "post",
        url: "/api/classroomManage/getClassroomInfo",
        headers: {
          'Content-Type': 'application/json;charset:utf-8;',
          // 'Bearer ' +
          'Authorization': this.route.query.token
        },
        params: this.postData
      }).then(res => {
        this.tableData = res.data.data
        this.changePage.currentPage = 1
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
    //重置选框
    reset(){
      this.classroom_building = ''
      this.classroom_floor = null
    },
    //预约
    order(){
      this.orderData.uid = this.route.query.uid
      // 判断是否符合输入条件
      axios({
        method: "post",
        url: "/api/classroomManage/orderClassroom",
        headers: {
          'Content-Type': 'application/json;charset:utf-8;',
          // 'Bearer ' +
          'Authorization': this.route.query.token
        },
        params: this.orderData
      }).then(res => {
        if (res.data.data === 'success'){
          ElMessageBox.alert('预约成功', '消息', {
            confirmButtonText: 'OK'
          })
        }
        if (res.data.data === 'userNotFound'){
          ElMessageBox.alert('该用户不存在', '消息', {
            confirmButtonText: 'OK'
          })
        }
        if (res.data.data === 'fail'){
          ElMessageBox.alert('预约失败', '消息', {
            confirmButtonText: 'OK'
          })
        }
      }).catch(err =>{
        console.log(err)
      })
    },
    //取消预约
    deorder(no){
      axios({
        method: "post",
        url: "/api/classroomManage/deorderClassroom",
        headers: {
          'Content-Type': 'application/json;charset:utf-8;',
          // 'Bearer ' +
          'Authorization': this.route.query.token
        },
        params: {clsNo:no,uid:this.route.query.uid}
      }).then(res => {
        if (res.data.data === 'success'){
          ElMessageBox.alert('已取消预约申请', '消息', {
            confirmButtonText: 'OK'
          })
        }else {
          ElMessageBox.alert('取消失败', '消息', {
            confirmButtonText: 'OK'
          })
        }
      }).catch(err =>{
        console.log(err)
      })
    },
    change(){
      this.selectorData.buildingName = this.classroom_building
      this.classroom_floor = ""
      axios({
        method: "post",
        url: "/api/classroomManage/getBuildingFloorSelector",
        headers: {
          'Content-Type': 'application/json;charset:utf-8;',
          // 'Bearer ' +
          'Authorization': this.route.query.token
        },
        params: this.selectorData
      }).then(res => {
        this.classroom_floor_ops = res.data.data
      }).catch(err =>{
        console.log(err)
      })
    }
  },
  created() {
    axios({
      method: "post",
      url: "/api/classroomManage/getBuildingSelector",
      headers: {
        'Content-Type': 'application/json;charset:utf-8;',
        // 'Bearer ' +
        'Authorization': this.route.query.token
      },
    }).then(res => {
      this.classroom_building_ops = res.data.data
    }).catch(err =>{
      console.log(err)
    })
  }
}
</script>

