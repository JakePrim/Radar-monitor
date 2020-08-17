// 路由根据网址的不同，返回不同的内容给用户
import Vue from 'vue'
import App from './App'
import router from './router'
// 重置默认的样式
import './assets/styles/reset.css'
// 解决1像素边框的问题
import './assets/styles/border.css'
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
