<template>
  <div class="space">
    <h2>欢迎来到我的美食空间</h2>
    <div class="user-info">
      <div class="user-avatar">
        <img :src="userInfo.avatar" alt="">
      </div>
      <div class="user-main">
        <h1>{{ userInfo.name }}</h1>
        <span class="info">
          <em>{{ userInfo.createdAt }}加入美食杰</em>
          |
          <router-link to="">编辑个人资料</router-link>
        </span>
        <div class="tools">
          <!-- follow-at no-follow-at -->
          <a href="javascript:;" v-if="!isOwner" class="follow-at" @click="handleFollowing"
             :class="{'no-follow-at':userInfo.isFollowing}">
            {{ userInfo.isFollowing ? '已关注' : '关注' }}
          </a>
        </div>
      </div>

      <ul class="user-more-info">
        <li>
          <div>
            <span>关注</span>
            <strong>{{ userInfo.following_len }}</strong>
          </div>
        </li>
        <li>
          <div>
            <span>粉丝</span>
            <strong>{{ userInfo.follows_len }}</strong>
          </div>
        </li>
        <li>
          <div>
            <span>收藏</span>
            <strong>{{ userInfo.collections_len }}</strong>
          </div>
        </li>
        <li>
          <div>
            <span>发布菜谱</span>
            <strong>{{ userInfo.work_menus_len }}</strong>
          </div>
        </li>
      </ul>
    </div>

    <!-- 菜单 activeName -->
    <el-tabs class="user-nav" v-model="activeName" @tab-click="handleTabClick">
      <el-tab-pane label="作品" name="works"></el-tab-pane>
      <el-tab-pane label="粉丝" name="fans"></el-tab-pane>
      <el-tab-pane label="关注" name="following"></el-tab-pane>
      <el-tab-pane label="收藏" name="collection"></el-tab-pane>
    </el-tabs>

    <div class="user-info-show">
      <!-- 显示二级路由页面 -->
      <router-view :info="infos" :activeName="activeName"></router-view>
    </div>

  </div>
</template>

<script>
import {userInfo, toggleFollowing, collection, following, fans, getMenus} from "@/service/request";

const activeTab = {
  async works(params){
    let data = (await getMenus(params)).data;
    data.flag = 'works';
    return data;
  },
  async fans(params){
    let data = (await fans(params)).data;
    data.flag = 'fans';
    return data;
  },
  async following(params){
    let data = (await following(params)).data;
    data.flag = 'following';
    return data;
  },
  async collection(params){
    let data = (await  collection(params)).data;
    data.flag = 'collection';
    return data;
  }
}
export default {
  name: "space",
  data() {
    return {
      userInfo: {},
      activeName: 'works',
      isOwner: false,
      infos:[]
    }
  },
  watch: {
    //监听路由变化
    $route: {
      //路由一变化就执行该方法
      async handler() {
        console.log(this.activeName);
        let {userId} = this.$route.query;
        //判断是否是当前用户自己
        this.isOwner = !userId || userId === this.$store.state.userInfo.userId;
        if (this.isOwner) {//进入自己的空间
          this.userInfo = this.$store.state.userInfo;
        } else {
          //进入别人的空间 请求接口
          const data = await userInfo({userId});
          this.userInfo = data.data;
        }
        //改变tab 名称防止刷新路由 activeName没有改变导致tab对应错误
        this.activeName = this.$route.name;
        //路由发生变化请求响应的信息
        this.getOtherInfo();
      },
      immediate: true
    }
  },
  methods: {
    async handleFollowing() {
      const data = await toggleFollowing({
        followUserId: this.userInfo.userId
      });
      this.userInfo = data.data;
    },
    handleTabClick(tab) {
      //当点击tab的时候 清空list数据
      this.infos = [];
      this.activeName = tab.name;
      //注意跳转的时候 要把之前路由的参数传递进去
      this.$router.push({
        name: this.activeName, query: {
          ...this.$route.query
        }
      });
    },
    async getOtherInfo() {
      //当切换tab的时候 最后一次回来的数据 就填充谁的数据
      //在返回的数据中添加请求的标识，如果和当前请求的标识相同就填充数据
      const data = await activeTab[this.activeName]({
        "userId": this.userInfo.userId
      });
      //判断返回数据的标记是否和当前的tab相同
      if (this.activeName === data.flag){
        this.infos = data.data.list;
      }
    }
  }
}
</script>

<style lang="stylus" scoped>
.space
  h2
    text-align center
    margin 20px 0

  .user-info
    height 210px
    background-color #fff
    display flex

    .user-avatar
      width 210px
      height 210px

      img
        width 100%
        height 100%

    .user-main
      width 570px
      padding 20px
      position relative

      h1
        font-size 24px
        color #333
        line-height 44px

      .info
        font-size 12px
        line-height 22px
        color #999

        a
          color #999

      .tools
        position absolute
        right 20px
        top 20px
        vertical-align top

        a
          display inline-block
          padding 3px 0
          width 50px
          color #fff
          text-align center

        a.follow-at
          background-color #ff3232

        a.no-follow-at
          background-color #999

    .user-more-info
      width 190px
      overflow hidden
      padding-top 20px

      li
        width 81px
        height 81px
        border-radius 32px
        border-bottom-right-radius 0
        margin 0px 8px 8px 0px
        float left

        div
          display block
          height 81px
          width 81px
          box-shadow 0px 0px 6px rgba(0, 0, 0, 0.5) inset
          border-radius 32px
          border-bottom-right-radius 0

          span
            color #999
            line-height 20px
            display block
            padding-left 14px
            padding-top 14px

          strong
            display block
            font-size 18px
            color #ff3232
            font-family "Microsoft YaHei"
            padding-left 14px
            line-height 32px

  .user-nav
    margin 20px 0 20px 0

  .user-info-show
    min-height 300px
    background-color #fff
    padding-top 20px

  .el-tabs__items.is-active
    color #ff3232

  .el-tabs__active-bar
    background-color #ff3232

  .el-tabs__nav-wrap::after
    background-color transparent
</style>