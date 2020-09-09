import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

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
