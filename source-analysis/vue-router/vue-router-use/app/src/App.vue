<template>
  <div id="app">
    <div id="nav">
      <!-- 创建路由连接 -->
      <router-link to="/">Index</router-link>
      <router-link to="/blog">Blog</router-link>
      <router-link to="/photo">Photo</router-link>
      <router-link to="/detail/12">Detail</router-link>
    </div>
    <!-- 路由组件占位 -->
    <router-view/>
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
