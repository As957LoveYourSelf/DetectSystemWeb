<template >
  <div style="height: 100vh; overflow: hidden; background-image: linear-gradient(to right, #ff8177 0%, #ff867a 0%, #ff8c7f 21%, #f99185 52%, #cf556c 78%, #b12a5b 100%);">
    <div style="width: 400px; height: 300px; border-radius: 20px;
                margin: 150px auto; padding: 30px;
                background-image: linear-gradient(to right, #ff8177 0%,#b12a5b 100%,#ff867a 0%, #ff8c7f 21%, #f99185 52%,#cf556c 78%); ">
      <el-form
          ref="form"
          :model="formData"
          label-width="auto"
          label-position="left"
          size="large"
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

<script>
import { login } from '../utils/user'
import {useRouter} from "vue-router";
import md5 from "blueimp-md5";
export default {
  data(){
    return{
      formData: {
        name: 'admin',
        password: '123456',
      },
      router:useRouter()
    }
  },
  methods:{
    onSubmit(){
      let data = {
        uname: this.formData.name,
        psd: md5(this.formData.password)
      };
      console.log(data)
      login(data)
          .then(res => {
            // 判断是否登录成功
            if(res.data.loginState === 'success'){
              sessionStorage.setItem("userInfo", res.data.userInfo)
              sessionStorage.setItem("userToken", res.data.userToken)
              console.log(res.data)
              console.log('登录成功')
              this.router.push({
                name: 'Home',
                query:{uname:res.data.userInfo.uname}
              })
            }
            // console.log(res)
            else if (res.data.loginState === "unameUnCheck"){
              console.log("用户名不存在")
            }
            else if (res.data.loginState === "psdUnCheck"){
              console.log("密码错误")
            }else {
              console.log("error")
            }
          })
          .catch(err => {
            console.log(err)
          })
    },
    clearForm(){
      this.formData.name = ''
      this.formData.password = ''
    }
  }
}

</script>
