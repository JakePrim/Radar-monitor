<template>
  <div class="waterfall" ref="waterfall">
    <!-- 设置一个插槽 来添加内容 -->
    <slot></slot>
    <!-- 加载中view -->
    <div class="waterfall-loading" ref="loading">
      <i class="el-icon-loading"></i>
    </div>
  </div>
</template>

<script>
export default {
name: "container"
}
</script>

<style lang="stylus" scoped>
.waterfall-loading
  width 100%
  height 20px
  text-align center
</style>