
<template>
    <div class="icons_warp">
      <swiper :options="swiperOptions">
        <swiper-slide v-for="(page,index) of pages" :key="index">
          <div class="item" v-for='item of page' :key="item.id">
            <div class="image">
              <img class="icon_img" :src="item.imgUrl">
            </div>
            <div class="icon_title">{{item.name}}</div>
          </div>
        </swiper-slide>
        <div class="swiper-pagination" slot="pagination"></div>
      </swiper>
    </div>
</template>

<script>
export default {
  name: 'HomeIcons',
  data: function () {
    return {
      swiperOptions: {
        pagination: '.swiper-pagination'
        // loop: true// 循环轮播
      },
      iconList: [
        {
          id: 1,
          name: '景点门票',
          imgUrl: 'http://img1.qunarzz.com/piao/fusion/1803/95/f3dd6c383aeb3b02.png'
        },
        {
          id: 2,
          name: '景点门票',
          imgUrl: 'http://img1.qunarzz.com/piao/fusion/1803/95/f3dd6c383aeb3b02.png'
        },
        {
          id: 3,
          name: '景点门票',
          imgUrl: 'http://img1.qunarzz.com/piao/fusion/1803/95/f3dd6c383aeb3b02.png'
        },
        {
          id: 4,
          name: '景点门票',
          imgUrl: 'http://img1.qunarzz.com/piao/fusion/1803/95/f3dd6c383aeb3b02.png'
        },
        {
          id: 5,
          name: '景点门票',
          imgUrl: 'http://img1.qunarzz.com/piao/fusion/1803/95/f3dd6c383aeb3b02.png'
        },
        {
          id: 6,
          name: '景点门票',
          imgUrl: 'http://img1.qunarzz.com/piao/fusion/1803/95/f3dd6c383aeb3b02.png'
        },
        {
          id: 7,
          name: '景点门票',
          imgUrl: 'http://img1.qunarzz.com/piao/fusion/1803/95/f3dd6c383aeb3b02.png'
        },
        {
          id: 8,
          name: '景点门票',
          imgUrl: 'http://img1.qunarzz.com/piao/fusion/1803/95/f3dd6c383aeb3b02.png'
        },
        {
          id: 9,
          name: '景点门票',
          imgUrl: 'http://img1.qunarzz.com/piao/fusion/1803/95/f3dd6c383aeb3b02.png'
        }
      ]
    }
  },
  computed: {
    // 计算属性 计算页数
    pages () {
      const pagesList = []
      this.iconList.forEach((item, index) => {
        // 计算页数，每页8个元素
        const page = Math.floor(index / 8)
        if (!pagesList[page]) {
          pagesList[page] = []
        }
        pagesList[page].push(item)
      })
      return pagesList
    }
  }
}
</script>
<style lang="stylus" scoped>
  @import '~styles/varibles.styl'
  @import '~styles/mixins.styl'
  .icons_warp >>> .swiper-container
    height: 0
    padding-bottom: 50%
    .item
      width : 25%
      height : 0
      position: relative
      padding-bottom : 25%
      overflow : hidden
      float : left
      .image
        position : absolute
        top:0
        left :0
        right :0
        box-sizing : border-box
        bottom: .44rem
        padding: .1rem
        .icon_img
          display : block
          margin : 0 auto
          height : 100%
      .icon_title
        position : absolute
        left :0
        right :0
        bottom:0
        text-align :center
        height: .44rem
        line-height: .44rem
        color: $darkTextColor
        ellipsis()
</style>
