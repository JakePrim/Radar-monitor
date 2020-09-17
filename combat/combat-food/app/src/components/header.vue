<template>
  <el-header style="height: auto;">
    <div class="header">
      <div class="header_c">
        <el-row type="flex" justify="start" align="middle">
          <!-- span设置占据的列数 默认有24列 进行分割 可根据需要动态设置 -->
          <el-col :span="6">
            <a href="" class="logo"></a>
          </el-col>
          <!-- offset 栅格左侧的间隔格数 -->
          <el-col :span="10" :offset="2"></el-col>
          <el-col :span="6" :offset="3">
            <router-link to="">
              <el-avatar style="vertical-align: middle;" shape="square" size="medium"
                         src=""></el-avatar>
            </router-link>
            <router-link to="" class="user-name">

            </router-link>
            <router-link to="" class="collection">
              发布菜谱
            </router-link>
            <a href="" class="collection">退出</a>
          </el-col>
          <el-col :span="6" :offset="3" class="avatar-box">
            <router-link to="" class="user-name">登录</router-link>
            <router-link to="" class="collection">退出</router-link>
          </el-col>
        </el-row>
      </div>
    </div>
    <div class="nav-box">
      <div class="nav_c">
        <Menus></Menus>
      </div>
    </div>
  </el-header>
</template>
<script>
import Menus from '@/components/menus'
export default {
  name: "Header",
  components:{
    Menus
  }
}
</script>
<style lang="stylus" scoped>
.header
  height 128px
  background-color #c90000
  .logo
    display block
    height 129px
    width 184px
    background url("https://s1.c.meishij.net/n/images/logo2.png") -15px 9px no-repeat
  .user-name
    margin-left 5px
    color #fff
.header_c,.nav_c
  width 990px
  margin 0 auto
.nav-box
  height 60px
  background-color #fff
  box-shadow  10px 0px 10px rgba(0,0,0,0.3)
</style>