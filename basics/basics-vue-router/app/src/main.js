import Vue from 'vue'
import App from './App.vue'
import router from './router'

//引入全局的css文件
import '@/assets/static/css/css.css'

Vue.config.productionTip = false
//bus用于组件之间的通信
Vue.prototype.bus = new Vue();
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
