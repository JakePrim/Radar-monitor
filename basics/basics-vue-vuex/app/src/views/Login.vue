<template>
  <div id="main">
    <div id="login" class="panel">
      <h2>登录</h2>
      <form @submit="checkFrom" method="post">
        <div class="form-item">
          <label>
            <span class="txt">姓名：</span>
            <input type="text" class="form-input" v-model="name" />
          </label>
        </div>
        <div class="form-item">
          <label>
            <span class="txt">密码：</span>
            <input type="password" class="form-input" v-model="password" />
          </label>
        </div>
        <div class="form-item">
          <label>
            <span class="txt"></span>
            <button class="form-button primary">登录</button>
          </label>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import Axios from "@/api";
export default {
  name: "Login",
  data: function () {
    return {
      name: "",
      password: "",
    }
  },
  methods: {
    checkFrom:async function(e) {
      e.preventDefault();
      console.log(Axios);
      //请求接口
      console.log(this.name,this.password);
      const data = await Axios.login({
          name:this.name,
          password:this.password
      });
      console.log(data);
      //登录成功之后跳转到首页 重定向
      const token = data.headers.authorization;
      localStorage.setItem("token", token);
      this.$router.push({name: `Home`});
      // this.bus.$emit('login',this.name);
      //vuex 的使用方式
      this.$store.commit('increment',this.name)
    },
  },
};
</script>