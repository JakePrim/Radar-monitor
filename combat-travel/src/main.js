// 路由根据网址的不同，返回不同的内容给用户
import Vue from 'vue'
import App from './App'
import router from './router'
// 重置默认的样式 style是一个别名指向：src/assets/styles简化目录
import 'styles/reset.css'
// 解决1像素边框的问题
import 'styles/border.css'
// 引入iconfont @ 符号代表src目录
import 'styles/iconfont.css'
import fastclick from 'fastclick'

Vue.config.productionTip = false
// 移动端300ms点击事件延迟的问题解决
fastclick.attach(document.body)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
