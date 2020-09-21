<template>
  <div>
    个人空间
  </div>
</template>

<script>
import {userInfo} from "@/service/request";

export default {
  name: "space",
  data(){
    return {
      userInfo:{}
    }
  },
  watch:{
    //监听路由变化
    $route:{
      async handler(){
        let {userId} = this.$route.query;
        if (!userId){//进入自己的空间
          this.userInfo = this.$store.state.userInfo;
        }else{
          //进入别人的空间
          const data = await userInfo({userId});
          console.log(data);
          this.userInfo = data.data;
        }
      },
      immediate:true
    }
  }
}
</script>

<style scoped>

</style>