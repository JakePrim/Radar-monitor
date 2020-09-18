<template>
  <div class="waterfall" ref="waterfall">
    <!-- 设置一个插槽 来添加内容 -->
    <slot></slot>
    <!-- 加载中view -->
    <div class="waterfall-loading" v-show="isLoading" ref="loading">
      <i class="el-icon-loading"></i>
    </div>
  </div>
</template>

<script>
//组件的下边距是否小于可视区的高度
import {throttle} from 'throttle-debounce';
export default {
  name: "container",
  data(){
    return {
      isLoading:false,
      scrollHandler:null
    }
  },
  mounted() {
    //优化每隔一段时间再去执行函数 不用频繁触发 节流函数
    this.scrollHandler = throttle(300,this.scroll.bind(this));
    //注意事件需要解绑
    window.addEventListener('scroll',this.scrollHandler);
  },
  methods:{
    scroll(){
      console.log(this.isLoading);
      if (this.isLoading) return;
      if (this.$refs.waterfall.getBoundingClientRect().bottom < document.documentElement.clientHeight) {
        console.log('底部已经到达可视区域');
        this.isLoading=true;
        this.$emit('view');
      }
    }
  },
  destroyed() {
    window.removeEventListener('scroll',this.scrollHandler)
  }
}
</script>

<style lang="stylus" scoped>
.waterfall-loading
  width 100%
  height 20px
  text-align center
</style>