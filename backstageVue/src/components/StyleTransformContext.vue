<template>
  <section>
    <div class="container">
      <div class="background-img">
        <div class="box">
          <span></span>
          <span></span>
          <span></span>
          <span></span>
          <div class="content">
            <el-upload
                drag
                class="img"
                action="/api/upload"
                accept="image/*"
                :headers="postHeader"
                :file-list="filelist"
                :on-success="handleAvatarSuccess"
                :on-change="handleChange"
                :before-upload="beforeAvatarUpload"
            >
              <el-image :src="imgUrl" fit="contain">
              </el-image>
            </el-upload>
          </div>
        </div>
      </div>
    </div>
    <el-button class="btn" type="primary" @click="transform">
      开始转化
    </el-button>
    <el-select v-model="value" class="selector" placeholder="请选择风格" size="large">
      <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
      />
    </el-select>
  </section>

</template>

<script>
import {defaultStyleTran} from "../utils/algorithm";

export default {
  name: "StyleTransformContext",
  data(){
    return{
      postHeader:{Authorization:sessionStorage.getItem("userToken")},
      imgBytes:null,
      imgUrl:"",
      filelist:[],
      value:0,
      options:[
        {
          value:0,
          label:"动漫人物"
        },
        {
          value:1,
          label:"动漫风景"
        },
        {
          value:2,
          label:"新海诚动漫"
        },
      ]
    }
  },
  methods: {
    handleChange(file, fileList) {
      if (fileList.length > 0) {
        this.filelist = [fileList[fileList.length - 1]]
      } else {
        this.filelist = fileList[0]
      }
    },
    transform() {
      const postData = {img: this.imgBytes, type: this.value, uname:"admin"}
      // console.log(postData)
      let loading = this.$loading({
        lock:true,
        text:"算法运行中"
      })
      defaultStyleTran(postData).then(res => {
        if (res.data != null){
          this.imgBytes = res.data
          this.imgUrl = 'data:image/jpeg;base64,' + res.data
          loading.close()
          this.$message.success("转化成功!")
        }else {
          loading.close()
          this.$message.success("转化失败!")
        }
      })
    },
    beforeAvatarUpload(file) {
      // 设置限定格式
      const img_mimetype = ['image/jpeg', 'image/jpg', 'image/png']
      const isJPG = img_mimetype.includes(file.type);
      const isLt2M = file.size / 1024 / 1024 < 20;
      if (!isJPG) {
        this.$message({
          type: 'error',
          message: '上传头像只能是图片格式!'
        })
        return false;
      }
      if (!isLt2M) {
        this.$message({
          type: 'error',
          message: '上传图片大小不能超过 20MB!'
        })
        return false;
      }
      return isJPG && isLt2M;
    },
    handleAvatarSuccess(res) {
      // 从后端得到上传后的图片url
      this.imgBytes = res.data
      this.imgUrl = 'data:image/jpeg;base64,' + res.data
      // console.log(this.imgUrl)
      this.$message({
        type: 'success',
        message: '图片上传成功'
      })
    }
  }
}
</script>

<style scoped>
.img{
  position: absolute;
  top: 50%;
  left: 50%;
  width: 500px;
  height: 700px;
  transform: translate(-50%,-50%);
}
.selector{
  position: absolute;
  top: 96%;
  left: 60%;
  width: 150px;
  height: 50px;
  transform: translate(-50%,-50%);
}
.btn{
  position: absolute;
  top: 95%;
  left: 45%;
  width: 150px;
  height: 50px;
  transform: translate(-50%,-50%);
}
.container{
  padding-top: 20px;
  padding-bottom: 20px;
}

.background-img{
  height: 720px;
  width: 1280px;
  background-repeat: no-repeat;
  background-size: cover;
  margin-left: 50px;
  margin-top: 40px;
  padding:20px;
  border: 1px solid #2a3cad;
  border-radius: 4px;
  box-shadow: 0px 0px 5px #2a3cad;
  position: relative;
}

.content h2{ font-size:19px;}

.box{
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 1280px;
  height: 720px;
  box-sizing: border-box;
  overflow: hidden;
  box-shadow: 0 20px 50px rgb(23, 32, 90);
  border: 2px solid #2a3cad;
  color: white;
  padding: 20px;
}

.box:before{
  content: '';
  position:absolute;
  top:0;
  left:-100%;
  width:100%;
  height:100%;
  background: rgba(255,255,255,0.1);
  transition:0.5s;
  pointer-events: none;
}

.box:hover:before{
  left:-50%;
  transform: skewX(-5deg);
}


.box .content{
  position:absolute;
  top:15px;
  left:15px;
  right:15px;
  bottom:15px;
  border:1px solid #f0a591;
  padding:20px;
  text-align:center;
  box-shadow: 0 5px 10px rgba(9,0,0,0.5);

}

.box span{
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: block;
  box-sizing: border-box;

}

.box span:nth-child(1)
{
  transform:rotate(0deg);
}

/*.box span:nth-child(2)*/
/*{*/
/*  transform:rotate(90deg);*/
/*}*/

.box span:nth-child(3)
{
  transform:rotate(180deg);
}

/*.box span:nth-child(4)*/
/*{*/
/*  transform:rotate(270deg);*/
/*}*/

.box span:before
{
  content: '';
  position: absolute;
  width:100%;
  height: 2px;
  background: rebeccapurple;
  animation: animate 4s linear infinite;
}

@keyframes animate {
  0% {
    transform: scaleX(0);
    transform-origin: left;
  }
  50% {
    transform: scaleX(1);
    transform-origin: left;
  }
  50.1% {
    transform: scaleX(1);
    transform-origin: right;

  }
  100% {
    transform: scaleX(0);
    transform-origin: right;

  }
}
</style>
