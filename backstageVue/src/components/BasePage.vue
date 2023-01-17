<script setup>
import {
  User,
  UserFilled,
  Setting,
  House,
  Shop,
} from '@element-plus/icons-vue'
</script>
<template>
  <div>
    <div style="height: 65px; line-height: 65px; background-color: #696969">
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
          <div style="width: 200px; text-align: center">
            User
          </div>
        </div>
      </div>
    </div>

    <div style="display: flex">
      <!-- 侧边栏 -->
      <div style="width: 300px;min-height: calc(100vh - 65px); background-color: #272727">
        <el-menu
            active-text-color="#ffd04b"
            background-color="#272727"
            class="el-menu-vertical-demo"
            default-active="2"
            text-color="#fff"
        >
          <el-menu-item index="1">
            <template #title>
              <el-icon><House /></el-icon>
              <span>班级管理</span>
            </template>
          </el-menu-item>
          <el-menu-item index="2">
            <el-icon><User /></el-icon>
            <span>学生管理</span>
          </el-menu-item>
          <el-menu-item index="3">
            <el-icon><UserFilled /></el-icon>
            <span>教师管理</span>
          </el-menu-item>
          <el-menu-item index="4">
            <el-icon><Shop /></el-icon>
            <span>教室管理</span>
          </el-menu-item>
          <el-menu-item index="5">
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
