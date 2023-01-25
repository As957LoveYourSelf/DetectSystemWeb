<template>
  <div style="position: relative;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            margin: auto">
    <el-descriptions
        title="教职工信息"
        direction="vertical"
        :column="4"
        :size="size"
        border
    >
      <div>
        <el-descriptions-item label="教师姓名" v-model="data.name"></el-descriptions-item>
        <el-descriptions-item label="所属学院" v-model="data.collage"></el-descriptions-item>
        <el-descriptions-item label="年龄" v-model="data.age" :span="2"></el-descriptions-item>
        <el-descriptions-item label="教学班级" v-model="data.classes" :span="2"></el-descriptions-item>
        <el-descriptions-item label="教学课程" v-model="data.courses" :span="2"></el-descriptions-item>
        <el-descriptions-item label="邮箱" v-model="data.email" :span="2"></el-descriptions-item>
        <el-descriptions-item label="教工号" v-model="data.no" :span="2"></el-descriptions-item>
        <el-descriptions-item label="电话" v-model="data.phone" ></el-descriptions-item>

      </div>
    </el-descriptions>

  </div>

</template>

<script setup>
import { computed, ref } from 'vue'
const size = ref('large');
const data = [

]
</script>
