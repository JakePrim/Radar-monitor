import Vue from 'vue'
import App from './App.vue'
import router from './router'
import Vuex from 'vuex'
import 'es6-promise/auto'

//引入全局的css文件
import '@/assets/static/css/css.css'

Vue.config.productionTip = false
//bus用于组件之间的通信
Vue.prototype.bus = new Vue();

Vue.use(Vuex)

const store = new Vuex.Store({
  state:{
    username:""
  },
  mutations:{
    increment(state,name){
      console.log('??',state);
      state.username = name;
    }
  }
});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
