<template>
  <div class="fans">
    <div class="info-empty" v-if="!info.length">
      <div>
        <p v-if="activeName==='fans'">还没有被关注哦，多发布菜谱，更容易被找到</p>
        <p v-if="activeName==='following'">还没有关注别人哦，可以预览菜谱，找到别人</p>
      </div>
    </div>
    <ul class="fans clearfix">
      <router-link :to="{name:'space',query:{userId:item.userId}}" tag="li" v-for="item of info" :key="item._id">
        <a href="" class="img">
          <img :src="item.avatar" alt="">
        </a>
        <div class="c">
          <strong class="name">
            <router-link :to="{name:'space',query:{userId:item.userId}}">{{ item.name }}</router-link>
          </strong>
          <em class="info"><span>粉丝:</span>{{ item.follows_len }} | <span>关注：</span>{{ item.following_len }}</em>
          <em class="info"><span>简介</span>{{ (item.sign || item.sign === '') ? `这个人太懒啦！还没有介绍自己` : item.sign }}</em>
        </div>
      </router-link>
    </ul>
  </div>
</template>

<script>
export default {
  name: "fans",
  props: {
    info: {
      type: Array,
      default: () => []
    },
    activeName:{
      type:String
    }
  }
}
</script>

<style lang="stylus">
.fans
  font-size 12px

.fans li
  height 80px
  width 950px
  border-bottom 1px solid #eee
  padding 20px 20px
  position relative


.fans lihover
  background #fafafa


.fans li a.img
  float left
  height 80px
  width 80px
  position relative


.fans li a.img img
  height 80px
  width 80px
  display block


.fans li div.c
  float left
  height 80px
  width 850px
  padding-left 14px


.fans li div.c strong.name
  display block
  height 32px
  line-height 32px
  font-size 14px
  color #333
  font-weight bold


.fans li div.c strong.name a
  color #333


.fans li div.c strong.name a span
  color #ff3232


.fans li div.c strong.name ahover
  color #ff3232
  text-decoration underline


.fans li div.c p
  font-size 14px
  color #666
  line-height 24px
  height 48px
  overflow hidden


.fans li div.c p a
  color #666


.fans li div.c p ahover
  color #ff3232
  text-decoration underline


.fans li div.c p strong
  font-size 12px
  padding-right 10px


.fans li div.c p strong em
  color #ff3232
  padding-right 4px


.fans li div.c span.time
  position absolute
  height 20px
  line-height 20px
  right 20px
  top 20px
  color #999


.fans li div.c span.time a
  color #999
  padding-left 10px
  display none


.fans li div.c span.time ahover
  color #ff3232


.fans li div.c em.info
  height 24px
  line-height 24px
  display block
  color #999


.fans li div.c em.info span
  color #666


</style>

