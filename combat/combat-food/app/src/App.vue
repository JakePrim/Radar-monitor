<template>
  <div id="app">
    <el-container>
      <Header></Header>
      <el-main>
        <div class="main">
          <!-- 路径对应的组件会替换在这个位置 -->
          <router-view/>
        </div>
      </el-main>
      <el-footer>
        <div class="footer">
          Copyright © 2019 - 2020
        </div>
      </el-footer>
    </el-container>
  </div>
</template>
<script>
import Header from '@/components/header'
export default {
  //添加子组件
  components:{
    Header
  }
}
</script>

<style lang="stylus">
body
  background #fae8c8
  font-size 12px
.main
  width 990px
  margin 0 auto
.footer
  text-align center
  height 42px
  line-height 42px
</style>
