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
                  accept="image/*"
                  :limit="1"
                  :on-success="handleAvatarSuccess"
                  :before-upload="beforeAvatarUpload"
              >
                <el-image :src="imgUrl">

                </el-image>
              </el-upload>
          </div>
        </div>
      </div>
    </div>
  </section>

</template>

<script>
export default {
  name: "SuperResolutionContext",
  data(){
    return{
      imgUrl:"https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg"
    }
  },
  methods:{
    beforeAvatarUpload(file) {
      // 设置限定格式
      const img_mimetype = ['image/jpeg','image/jpg','image/png']
      const isJPG =  img_mimetype.includes(file.type);
      const isLt2M = file.size / 1024 / 1024 < 16;
      if (!isJPG) {
        this.$message.error('上传头像只能是图片格式!');
        return false;
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 16MB!');
        return false;
      }
      return isJPG && isLt2M;
    },
    handleAvatarSuccess(res){
      // 从后端得到上传后的图片url
      this.imgUrl = res.data
    },

  }
}
</script>
<style scoped>
.img{
  position: absolute;
  top: 50%;
  left: 50%;
  width: 300px;
  height: 300px;
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
  background: #111845a6;
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
  background: #50dfdb;
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
