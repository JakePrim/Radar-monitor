<template>
  <div>
    <div>
      <img src="@/assets/logo.png" alt="">
    </div>
    <div>
      <router-view></router-view>
    </div>
    <div>
      Footer
    </div>
  </div>
</template>

<script>
export default {
  name: 'Layout'
}
</script>

<style scoped>

</style>
