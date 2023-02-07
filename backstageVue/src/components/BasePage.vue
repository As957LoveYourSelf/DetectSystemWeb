<script setup>
import {
  User,
  UserFilled,
  Setting,
  House,
  Shop,
  HomeFilled,
  Box,
  Tickets
} from '@element-plus/icons-vue'
import {reactive, ref, toRefs, watch} from "vue";
import {useRouter} from "vue-router";
const state = reactive({
  circleUrl:
      'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
})
const {circleUrl} = toRefs(state)
const router = useRouter()
let current_route = ref('')
watch(() => router.currentRoute.value.path, (path) =>{
  current_route = path
  console.log(current_route)
},{immediate: true,deep: true})
</script>
<template>
  <div>
    <div style="height: 65px; line-height: 65px; background-image: linear-gradient(120deg, #a1c4fd 0%, #c2e9fb 100%); ">
      <div style="display: flex">
        <!-- Logo -->
        <div style="width: 300px; text-align: center; font-size: 30px; font-weight: bold; font-family: 'Comic Sans MS'; color: bisque">
          FreeTimeBackStage
        </div>
        <div style="flex: 1; display: flex">
          <!-- 顶部菜单 -->
          <div style="flex: 1">
          </div>
          <!-- 用户头像 -->
          <div style="width: 100px; margin-top: 6px" class="block">
            <el-avatar :size="50" :src="circleUrl" />
          </div>
        </div>
      </div>
    </div>

    <div style="display: flex;background-image: linear-gradient(to top, #fbc2eb 0%, #a6c1ee 100%);">
      <!-- 侧边栏 -->
      <div style="width: 300px;min-height: calc(100vh - 65px); background-color: #272727">
        <el-menu
            active-text-color="#ffd04b"
            background-color="#272727"
            class="el-menu-vertical-demo"
            default-active=""
            text-color="#fff"
            router
        >
          <el-menu-item index="/home">
            <el-icon><House /></el-icon>
            <span>首页</span>
          </el-menu-item>
          <el-menu-item index="/class">
            <el-icon><HomeFilled /></el-icon>
            <span>班级管理</span>
          </el-menu-item>
          <el-menu-item index="/student">
            <el-icon><User /></el-icon>
            <span>学生管理</span>
          </el-menu-item>
          <el-menu-item index="/majorCourse">
            <el-icon><Tickets /></el-icon>
            <span>课程管理</span>
          </el-menu-item>
          <el-menu-item index="/teacher">
            <el-icon><UserFilled /></el-icon>
            <span>教师管理</span>
          </el-menu-item>
          <el-menu-item index="/classroom">
            <el-icon><Shop /></el-icon>
            <span>教室管理</span>
          </el-menu-item>
          <el-menu-item index="/todo">
            <el-icon><Box /></el-icon>
            <span>待处理请求</span>
          </el-menu-item>
          <el-menu-item index="/setting">
            <el-icon><setting /></el-icon>
            <span>设置</span>
          </el-menu-item>
        </el-menu>
      </div>
      <slot>
        Context
      </slot>
    </div>

  </div>
</template>
