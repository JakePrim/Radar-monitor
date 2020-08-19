<template>
  <header id="header">
    <a href="/" id="logo"></a>

    <nav id="nav">
      <a href>手机</a>
      <a href>笔记本</a>
      <a href>家居</a>
    </nav>

    <div id="user">
      <div v-if="isLogin">
        <span>{{this.username}}</span>
      </div>
      <div v-else>
        <router-link :to="{name:'Login'}" tag="a">登录</router-link>
        <router-link :to="{name:'Register'}" tag="a">注册</router-link>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  name: "HomeHeader",
  data: function () {
    return {
      username: "",
      isLogin: false,
    };
  },
  created() {
    console.log("header");
  },
  mounted() {
    this.bus.$on("login", (name) => {
      this.isLogin = true;
      this.username = name;
    });
  },
};
</script>