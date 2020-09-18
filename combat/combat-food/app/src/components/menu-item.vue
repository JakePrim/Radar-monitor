<template>
  <el-row class="menu-card" type="flex" justify="start">
    <el-col style="flex:none;" :style="{'margin-left':marginLeft+'px'}" v-for="item of info" :key="item._id">
      <el-card :body-style="{padding:'0px'}">
        <router-link :to="{name:'detail',query:{id:item._id}}">
          <img :src="item.product_pic_url" alt="" class="image" style="width: 232px;height: 232px">
          <div style="padding: 14px" class="menu-card-detail">
            <strong>{{item.title}}</strong>
            <span>{{item.comments_len}} 评论</span>
            <!-- 跳转到用户页面 -->
            <router-link :to="{name:'space',query:{userId:item.userId}}" tag="em">
              {{item.name}}
            </router-link>
          </div>
        </router-link>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "menu-item",
  props:{
    marginLeft:{
      type:Number,
      default:22
    },
    info:{
      type:Array,
      default: ()=>[]
    }
  }
}
</script>

<style lang="stylus" scoped>
.menu-card
  flex-wrap wrap;
  .el-col-24
    width auto
    margin-bottom 20px
    margin-left 22px
  .menu-card-detail
    >*
      display block
    strong
      height 24px
      line-height 24px
      font-size 14px
      font-weight bold;
      color #333
    span
      height 26px
      line-height 26px
      font-size 12px
      color #ff3232
</style>