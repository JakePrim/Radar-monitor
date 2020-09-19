<template>
  <div class="login-page">
    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="登录" name="login">
        <Login/>
      </el-tab-pane>
      <el-tab-pane label="注册" name="second">
        <Register/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import Login from '@/views/login/login';
import Register from '@/views/login/register';

export default {
  name: "index",
  components: {
    Login,
    Register
  },
  data() {
    return {
      activeName: 'login'
    }
  }
}
</script>

<style lang="stylus" scoped>
.login-page
  width 500px
  background-color #fff
  margin 0 auto
  box-shadow 0 0 25px #cac6c6;
  border-radius 10px
</style>