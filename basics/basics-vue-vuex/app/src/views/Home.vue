<template>
  <div id="main">
    <ul class="items-list">
      <router-link
        :to="{
        name:'Detail',
        params:{
          id:item.id
        }
      }"
        v-for="item of items"
        :key="item.id"
        tag="li"
        class="panel"
      >
        <img :src="item.cover" alt class="cover" />
        <div class="name">{{item.name}}</div>
        <div class="price">{{item.price}}</div>
      </router-link>
    </ul>
    <page :page="page" :total="total" :prepage="prepage" @change="pageChange"></page>
  </div>
</template>

<script>
import Axios from "@/api";
import Page from "@/components/Page";
export default {
  name: "Home",
  components: {
    Page,
  },
  data: function () {
    return {
      items: [],
      page: 1,
      total: 0,
      prepage: 8,
    };
  },
  async created() {
    const token = localStorage.getItem("token");
    console.log(token);
    if (token) {
      console.log("登录成功");
      await this.getItems();
    } else {
      this.$router.push({ name: "Login" });
    }
  },
  watch: {
    async $route() {
      console.log("路由变化了");
      //监听路由变化
      await this.getItems();
    },
  },
  methods: {
    async getItems() {
      this.page = this.$route.query.page || 1;
      const {
        data: { items, page, total, prepage },
      } = await Axios.getItems({
        page: this.page,
        prepage: this.prepage,
      });
      this.items = items;
      this.page = page;
      this.total = total;
      this.prepage = prepage;
    },
    async pageChange(p) {
      console.log(p);
      // 通过querystring来改变当前页面当url
      // 如果路由两次跳转使用当是同一个组件的时候，这个组件是不会销毁然后重建的，而是复用，created生命周期不会执行
      // $router: 实际上就是 new VueRouter 得到的对象，它代表来整个应用的路由信息，提供路由公用的配置信息和方法，比如 push
      // $route: 满足当前url而创建的一个对象，这个对表里面存储与当前url匹配的路由信息
      this.$router.push({
        name: "Home",
        query: {
          page: p,
        },
      });
    },
  },
};
</script>
