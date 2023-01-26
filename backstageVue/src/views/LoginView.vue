<template >
  <div style="height: 100vh; overflow: hidden; background-image: linear-gradient(to right, #ff8177 0%, #ff867a 0%, #ff8c7f 21%, #f99185 52%, #cf556c 78%, #b12a5b 100%);">
    <div style="width: 400px; height: 300px; border-radius: 20px;
                margin: 150px auto; padding: 30px;
                background-image: linear-gradient(to right, #ff8177 0%,#b12a5b 100%,#ff867a 0%, #ff8c7f 21%, #f99185 52%,#cf556c 78%); ">
      <el-form
          ref="form"
          :model="formData"
          label-width="auto"
          :label-position="labelPosition"
          :size="size"
      >
        <div>
          <el-form-item label="用户名">
            <el-input v-model="formData.name"/>
          </el-form-item>
        </div>

        <div>
          <el-form-item label="密码">
            <el-input v-model="formData.password" type="password"/>
          </el-form-item>
        </div>
        <div style="flex: 1;">
          <el-form-item>
            <el-button type="primary" @click="onSubmit">登录</el-button>
            <el-button type="warning" @click="clearForm">重置</el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { login } from '@/utils/user'
import md5 from "blueimp-md5";
const size = ref('large')
const labelPosition = ref('left')
const formData = reactive({
  name: 'admin',
  password: '123456',
})
function onSubmit() {
  let data = {
    uname:formData.name,
    psd:md5(formData.password)
  };
  login(data)
      .then(res => {
        // 判断是否登录成功
        console.log(res)
      })
      .catch(err => {
        console.log(err)
      })

}
function clearForm(){
  formData.name = ''
  formData.password = ''
}
</script>
