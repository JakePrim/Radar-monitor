<template>
  <div class="menu-list">
    <div class="info-empty" v-if="!info.length">
      <div v-if="activeName==='works'">
        <p>私房菜不要偷偷享用哦，制作成菜谱与大家分享吧</p>
        <a href="">发布菜单</a>
      </div>
      <div v-if="activeName==='collection'">
        <p>还没有收藏任何的菜谱，去搜自己喜欢的菜谱，收藏起来</p>
        <a href="">菜谱大全</a>
      </div>
    </div>
    <menu-item :margin-left="13" :info="info"></menu-item>
  </div>
</template>

<script>
import MenuItem from "@/components/menu-item";

export default {
  name: "menu-list",
  components: {MenuItem},
  props: {
    info: [],
    activeName:{
      type:String
    },
  },
  data() {
    return {}
  }
}
</script>

<style lang="stylus" scoped>
.info-empty
  width 100%
  min-height inherit
  display flex
  align-items center
  justify-content:center;
  p
    text-align center
    font-size 12px
  a
    text-align center
    display block
    height 48px
    width 200px
    line-height 48px
    font-size 14px
    background #ff3232
    color #fff
    font-weight bold
    margin 0px auto
</style>