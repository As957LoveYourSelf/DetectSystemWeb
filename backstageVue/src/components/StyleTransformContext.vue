<template>
  <el-upload
      class="upload-demo"
      drag
      action="imgUrl"
  >
  <el-image>

  </el-image>
  </el-upload>
</template>

<script>
export default {
  name: "StyleTransformContext",
  data(){
    return{
      imgUrl:''
    }
  },
}
</script>
