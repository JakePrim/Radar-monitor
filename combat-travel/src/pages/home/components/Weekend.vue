<template>
    <div>
        <div class="title">周末去哪儿</div>
        <ul>
            <li class="item border-bottom" v-for="item of recommendList" :key="item.id">
                <div class="item-img-wrapper">
                    <img class="item-img" :src="item.imgUrl"/>
                </div>
                <div class="item-info">
                    <p class="item-title">{{item.title}}</p>
                    <p class="item-desc">{{item.desc}}</p>
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
export default {
  name: 'Weenlend',
  data () {
    return {
      recommendList: [
        {
          id: '001',
          imgUrl: 'http://img1.qunarzz.com/sight/source/1811/f3/86173f863bef61.jpg_r_640x214_52b003ac.jpg',
          title: '大连人妖表演1',
          desc: '浪漫大连1'
        },
        {
          id: '002',
          imgUrl: 'http://img1.qunarzz.com/sight/source/1811/f3/86173f863bef61.jpg_r_640x214_52b003ac.jpg',
          title: '大连人妖表演2',
          desc: '浪漫大连2'
        },
        {
          id: '003',
          imgUrl: 'http://img1.qunarzz.com/sight/source/1811/f3/86173f863bef61.jpg_r_640x214_52b003ac.jpg',
          title: '大连人妖表演3',
          desc: '浪漫大连3'
        }
      ]
    }
  }
}
</script>

<style lang="stylus" scoped>
@import '~styles/mixins.styl'
.title
    line-height: 0.8rem
    background: #eee
    margin-top: 0.2rem
    text-indent: 0.2rem
.item-img-wrapper
    overflow : hidden
    height :0
    padding-bottom : 33.9%
    .item-img
        width: 100%
.item-info
    flex: 1
    padding: 0.1rem
    min-width: 0
.item-title
    line-height: 0.54rem
    font-size: 0.32rem
    ellipsis()
.item-desc
    line-height: 0.4rem
    color: #ccc
    ellipsis()
</style>
