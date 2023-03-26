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
                  class="img"
                  drag
                  action="/api/upload"
                  :headers="postHeader"
                  accept="image/*"
                  :file-list="filelist"
                  :on-success="handleAvatarSuccess"
                  :on-change="handleChange"
                  :before-upload="beforeAvatarUpload"
              >
                <el-image :src="imgUrl">

                </el-image>
              </el-upload>
          </div>
        </div>
      </div>
    </div>
    <el-button class="btn" type="primary" @click="enhance">
      开始超分
    </el-button>
  </section>

</template>

<script>
import {enhance} from "../utils/algorithm";
export default {
  name: "SuperResolutionContext",
  data(){
    return{
      postHeader:{Authorization:sessionStorage.getItem("userToken")},
      imgBytes:null,
      imgUrl:"",
      filelist:[]
    }
  },
  methods:{
    handleChange(file, fileList){
      if (fileList.length > 0) {
        this.filelist = [fileList[fileList.length - 1]]
      }else{
        this.filelist = fileList[0]
      }
    },
    enhance(){
      const postData = {img:this.imgBytes, uname:"admin"}
      // console.log(postData)
      let loading = this.$loading({
        lock:true,
        text:"算法运行中"
      })
      enhance(postData).then(res =>{
        if (res.data != null){
          this.imgBytes = res.data
          this.imgUrl = 'data:image/jpeg;base64,'+res.data
          loading.close()
          this.$message.success("转化成功!")
        }else {
          loading.close()
          this.$message.error("转化失败!")
        }
      })
    },
    beforeAvatarUpload(file) {
      // 设置限定格式
      const img_mimetype = ['image/jpeg','image/jpg','image/png']
      const isJPG =  img_mimetype.includes(file.type);
      const isLt2M = file.size / 1024 / 1024 < 16;
      if (!isJPG) {
        this.$message({
          type:'error',
          message:'上传头像只能是图片格式!'
        })
        return false;
      }
      if (!isLt2M) {
        this.$message({
          type:'error',
          message:'上传头像图片大小不能超过 16MB!'
        })
        return false;
      }
      return isJPG && isLt2M;
    },
    handleAvatarSuccess(res){
      // 从后端得到上传后的图片url
      this.imgBytes = res.data
      this.imgUrl = 'data:image/jpeg;base64,'+res.data
      // console.log(this.imgUrl)
      this.$message({
        type:'success',
        message:'图片上传成功'
      })
    },
    imageToBlob(src) {
      return new Promise((resolve,reject)=>{
        let img = new Image();
        img.setAttribute('crossOrigin', 'anonymous');
        img.src = src;
        img.onload = () => {
          let canvas = document.createElement("canvas");
          let ctx = canvas.getContext("2d");
          canvas.width = img.width;
          canvas.height = img.height;
          ctx.drawImage(img, 0, 0, img.width, img.height);
          let ext = img.src.substring(img.src.lastIndexOf(".") + 1).toLowerCase();
          let base64 = canvas.toDataURL("image/" + ext);
          let arr = base64.split(','),
              mime = arr[0].match(/:(.*?);/)[1],
              bstr = atob(arr[1]),
              n = bstr.length,
              u8arr = new Uint8Array(n);
          while (n--) {
            u8arr[n] = bstr.charCodeAt(n);
          }
          resolve(u8arr);
        }
      })
    }
  }
}
</script>
<style scoped>
.img{
  position: absolute;
  top: 30%;
  left: 50%;
  width: 300px;
  height: 300px;
  transform: translate(-50%,-50%);
}

.btn{
  position: absolute;
  top: 90%;
  left: 67%;
  width: 150px;
  height: 50px;
  transform: translate(-50%,-50%);
}
.container{
  padding-top: 20px;
  padding-bottom: 20px;
}

.background-img{
  height: 700px;
  width: 700px;
  background-repeat: no-repeat;
  background-size: cover;
  margin-left: 330px;
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
  width: 700px;
  height: 700px;
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

.box span:nth-child(2)
{
  transform:rotate(90deg);
}

.box span:nth-child(3)
{
  transform:rotate(180deg);
}

.box span:nth-child(4)
{
  transform:rotate(270deg);
}

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
