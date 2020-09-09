<template>
  <div class="wapper">
    <swiper ref="mySwiper" :options="swiperOptions">
        <swiper-slide v-for="item of swiperList" :key="item.id">
            <img class="swiper-img" :src="item.imgUrl">
        </swiper-slide>
        <div class="swiper-pagination" slot="pagination"></div>
    </swiper>
  </div>
</template>

<script>
export default {
  name: 'HomeSwiper',
  data: function () {
    return {
      swiperOptions: {
        pagination: '.swiper-pagination',
        loop: true// 循环轮播
      },
      swiperList: [
        {
          id: '0001',
          imgUrl: 'http://img1.qunarzz.com/piao/fusion/1802/e3/62ce7362ca051d02.jpg_640x200_6db551b7.jpg'
        },
        {
          id: '0002',
          imgUrl: 'http://img1.qunarzz.com/piao/fusion/1801/93/ce59d182aca07102.jpg_640x200_ba03d44c.jpg'
        }
      ]
    }
  }
}
</script>

<style lang="stylus" scoped>
    @import '~styles/varibles.styl'
      /* 定义图片的宽高比 */
    .wapper >>> .swiper-pagination-bullet-active
      background : #fff
    .wapper
        width : 100%
        height : 0
        padding-bottom : 30.45%
        background : #eee
        .swiper-img
          width :100%

</style>
