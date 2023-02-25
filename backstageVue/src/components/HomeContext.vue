<template>
  <div>
    <div>
      <span style="font-family: Cambria; font-size: xxx-large; font-weight: bolder; color: red">广告位招租</span>
    </div>
    <div style="flex: 1;">
      <span style="font-family: Cambria; font-size: xxx-large; font-weight: bolder; color: red">QQ邮箱: 1290450579@qq.com</span>
    </div>

  </div>

</template>

<script>
import {useRoute} from 'vue-router'
export default {
  data(){
    return{
      route:useRoute()
    }
  },
  created() {
    console.log(this.route.query)
  }
}

</script>
