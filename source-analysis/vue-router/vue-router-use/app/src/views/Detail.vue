<template>
  <div>
    这是详情页
    通过当前路由规则获取:{{ $route.params.id }}
    <br>
    推荐：通过开启props的方式获取:{{ id }}
    <button @click="go">go(-2)</button>
  </div>
</template>

<script>
export default {
  name: 'Detail',
  props: ['id'],
  methods: {
    go() {
      this.$router.go(-2);
    }
  }
}
</script>

<style scoped>

</style>
