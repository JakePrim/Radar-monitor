import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
//引入组件会在打包的时候 打包到一个文件中 如果所有的组件都打包到一个文件中，会导致文件很大


Vue.use(VueRouter)

//按需加载 ()=>import(../component) 如果所有都用import的话所有组件都会打包一个文件中，导致文件很大

  const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    //import 按需加载 只有在使用的时候才会加载
    //webpackChunkName 相同名称下 可以将多个组件合并成一个文件
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '*',// 没有匹配到路径 重定向到home组件
    name: 'noFound',
    title: '未找到',
    redirect: {
      name: 'home'
    }
  }
]

const router = new VueRouter({
  mode: 'history',// hash: #home    history: /home
  base: process.env.BASE_URL,
  routes
})

export default router
