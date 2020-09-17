<template>
  <div class="home">
    <!-- 轮播 -->
    <el-carousel :interval="5000" type="card" height="300px">
      <el-carousel-item v-for="item of bannerList" :key="item._id">
        <router-link to="">
          <img :src="item.product_pic_url" alt="" width="100%">
        </router-link>
      </el-carousel-item>
    </el-carousel>
    <!-- 内容列表 -->
    <div>
      <h2>内容精选</h2>
      <container>
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
      bannerList: []
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
      page: 1
    }).then(res => {
      console.log(res);
      this.menuList = res.data.list;
    }).catch(error => {
      console.log(error);
    })
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
    margin 0px

  .el-carousel__item:nth-child(2n)
    background-color #99a9bf

  .el-carousel__item:nth-child(2n+1)
    background-color #d3dce6
</style>
