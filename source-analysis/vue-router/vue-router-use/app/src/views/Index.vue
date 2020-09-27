<template>
  <div class="index">
    这里是首页
    <br>
    <router-link to="login">登录</router-link>
    <router-link to="detail/5">详情</router-link>
    <button @click="replace"> replace</button>
    <button @click="goDetail">Detail</button>
  </div>
</template>
<script>
export default {
  name: 'Index',
  methods: {
    replace() {
      this.$router.replace('/login');
    },
    goDetail() {
      this.$router.push({name: 'detail', params: {id: 1}});
    }
  }
}
</script>
