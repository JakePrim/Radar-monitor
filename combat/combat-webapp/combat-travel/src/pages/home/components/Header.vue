<template>
  <div class="header">
    <div class ="header-left">
        <div class="iconfont back-icon">&#xe624;</div>
    </div>
    <div class ="header-input">
        <span class="iconfont search-icon">&#xe632;</span>
        输入景点游玩、主题
    </div>
    <div class ="header-right">
        城市
        <span class="iconfont arrow-icon">&#xe62d;</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomeHeader'
}
</script>

<style lang="stylus" scoped>
  /* scoped 只对当前的组件有效 ~@ 代表src目录*/
    @import '~styles/varibles.styl'
    .header
        display: flex
        align-items :center
        height: .86rem
        background:$bgColor
        color: #fff
        .header-left
            width: .64rem
            float: left
            text-align :center
            .back-icon
                text-align : center
                font-size : .4rem
        .header-input
            flex: 1
            border-radius : .1rem
            background : #fff
            height :.64rem
            margin-left : .2rem
            color: #ccc
            line-height : .64rem
            .search-icon
                margin-left : 0.1rem
        .header-right
            width: 1.24rem
            float: right
            text-align:center
            .arrow-icon
                font-size : .24rem

</style>
