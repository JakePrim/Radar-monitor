<template>
  <div>
    登录页面
    <br>
    用户名: <input type="text"/>
    <br>
    密&nbsp;&nbsp;码:<input type="password"/> <br>
    <button @click="push">push</button>
    <router-link to="/">首页</router-link>
  </div>
</template>

<script>
export default {
  name: 'Login',
  methods: {
    push() {
      // 跳转路由的路径
      this.$router.push('/')
      // 跳转路由的名称 -- 推荐使用
      // this.$router.push({name: 'home' });
    }
  }
}
</script>

<style scoped>

</style>
