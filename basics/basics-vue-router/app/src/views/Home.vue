<template>
  <div id="main">
    <ul class="items-list">
      <li class="panel" v-for="item of items" :key="item.id">
        <img :src="item.cover" alt class="cover" />
        <div class="name">{{item.name}}</div>
        <div class="price">{{item.price}}</div>
      </li>
    </ul>
    <div class="pagination-container">
      <div class="pagination">
        <a href class="prev">上一页</a>
        <a href>1</a>
        <a href>2</a>
        <a href>3</a>
        <a href class="current">4</a>
        <a href>5</a>
        <a href>6</a>
        <a href>7</a>
        <a href>8</a>
        <a href class="next">下一页</a>
      </div>
    </div>
  </div>
</template>

<script>
import Axios from '@/api'
export default {
  name: "Home",
  data: function () {
    return {
      items: [],
    };
  },
  async created() {
    const token = localStorage.getItem("token");
    console.log(token);
    if (token) {
      console.log("登录成功");
      const data = await Axios.getItems();
      this.items = data.data.items;
      console.log(data);
    } else {
      this.$router.push({ name: "Login" });
    }
    //请求接口
  },
};
</script>
