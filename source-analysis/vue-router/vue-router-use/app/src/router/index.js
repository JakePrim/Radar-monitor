import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from '@/views/Index'

import Layout from '@/components/Layout'
import Login from '@/views/Login'

// 注册路由插件，他会调用传入对象的install 方法
Vue.use(VueRouter)

const routes = [
  // 嵌套路由
  {
    path: '/',
    name: 'home',
    component: Layout,
    children: [
      {
        name: 'index',
        path: '',
        component: Index
      },
      {
        name: 'detail',
        path: 'detail/:id',
        props: true,
        component: () => import(/* webpackChunkName: "detail" */ '@/views/Detail')
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/detail/:id',
    name: 'Detail',
    // 开启props，会把URL中的参数传递给组件 在组件中通过props来接受URL参数
    props: true,
    // 使用路由懒加载 当访问的时候才会加载
    component: () => import(/* webpackChunkName: "detail" */ '@/views/Detail')
  },
  {
    path: '/blog',
    name: 'Blog',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "blog" */ '@/views/Blog.vue')
  },
  {
    path: '/photo',
    name: 'Photo',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "photo" */ '@/views/Photo.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
