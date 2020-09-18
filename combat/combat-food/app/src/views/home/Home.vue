<template>
  <div class="home">
    <!-- 轮播 -->
    <el-carousel :interval="5000" type="card" height="300px">
      <el-carousel-item v-for="item of bannerList" :key="item._id">
        <!-- 路由跳转 -->
        <router-link :to="{name:'detail',query:{id:item._id}}">
          <img :src="item.product_pic_url" alt="" width="100%">
        </router-link>
      </el-carousel-item>
    </el-carousel>
    <!-- 内容列表 -->
    <div>
      <h2>内容精选</h2>
      <container @view="loadMore" ref="container">
        <menu-item :margin-left="13" :info="menuList"></menu-item>
      </container>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import Container from '@/components/container';
import MenuItem from '@/components/menu-item'
import {getBanner, getMenus} from '@/service/request';

export default {
  name: 'Home',
  components: {
    Container,
    MenuItem
  },
  data() {
    return {
      menuList: [],
      bannerList: [],
      page: 1,
      pages: 0
    }
  },
  mounted() {
    //请求接口
    getBanner().then(res => {
      this.bannerList = res.data.list;
    }).catch(error => {
      console.log(error);
    });
    getMenus({
      page: this.page
    }).then(res => {
      console.log(res);
      this.menuList = res.data.list;
      this.pages = Math.ceil(res.data.total / res.data.page_size);
      this.pages=3
    }).catch(error => {
      console.log(error);
    })
  },
  methods: {
    loadMore() {
      //请求更多的数据
      this.page++
      if (this.page > this.pages) {
        this.$refs.container.isLoading = false
        return;
      }
      //修改loading的状态
      this.$refs.container.isLoading = true
      getMenus({
        page: this.page
      }).then(res => {
        this.$refs.container.isLoading = false
        this.menuList.push(...res.data.list);
      }).catch(error => {
        console.log(error);
      })
    }
  }
}
</script>
<style lang="stylus" scoped>
.home
  h2
    text-align center
    padding 20px 0

  .el-carousel__item h3
    color #474669
    font-size 14px
    opacity 0.75
    line-height 200px
    margin 0

  .el-carousel__item:nth-child(2n)
    background-color #99a9bf

  .el-carousel__item:nth-child(2n+1)
    background-color #d3dce6
</style>
