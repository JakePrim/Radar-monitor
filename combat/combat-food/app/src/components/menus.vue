<template>
  <!-- default-active 当前激活菜单的index； mode 横向、竖向菜单模式；unique-opened 是否是保持一个子菜单的展开 -->
  <el-menu :default-active="curIndex" class="el-menu-demo" mode="horizontal" :unique-opened="true">
    <el-menu-item index="1">
      <router-link class="nva-link" :to="{name:'home'}">首页</router-link>
    </el-menu-item>
    <el-menu-item index="2">
      <router-link class="nav-link" :to="{name:'home'}">菜谱大全</router-link>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: "menus",
  data(){
    return {
      curIndex:"1",
    }
  }
}
</script>

<style scoped>
.nav-link {
  display: inline-block;
}
</style>