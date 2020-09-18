import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/home/Home.vue'

import '@/assets/css/index.styl'
import '@/assets/css/over-write.styl'

//引入组件会在打包的时候 打包到一个文件中 如果所有的组件都打包到一个文件中，会导致文件很大


Vue.use(VueRouter)
//import 按需加载 只有在使用的时候才会加载
//webpackChunkName 相同名称下 可以将多个组件合并成一个文件
//按需加载 ()=>import(../component) 如果所有都用import的话所有组件都会打包一个文件中，导致文件很大

const routes = [
    //Home 打包到一个文件中 其他组件按需加载
    {
        path: '/',
        name: 'home',
        component: Home
    },
    {
        path: '/login',
        name: 'login',
        title: '登录页',
        component: () => import('@/views/login/index'),
        meta: {//是否登录标记
            login: true,
        }
    },
    {
        path: '/detail',
        name: 'detail',
        title: '菜谱详情',
        component: () => import('@/views/detail/Detail')
    },
    {
        path: '/space',
        title: '个人空间',
        name: 'space',
        component: () => import('@/views/user/space'),
        redirect: {
            name:'works'//该组件下有多个组件 默认显示的组件名
        },
        meta: {//用来标记需要登录
            login: true,
        },
        children:[
            {
                path:'works',
                name:'works',
                title:'作品',
                component:()=>import('@/views/user/menu-list'),
                meta:{
                    login:true
                }
            },
            {
                path: 'fans',
                name: 'fans',
                title: '粉丝',
                component:()=>import('@/views/user/fans'),
                meta: {
                    login: true
                }
            },
            {
                path: 'fans',
                name: 'fans',
                title: '粉丝',
                component:()=>import('@/views/user/fans'),
                meta: {
                    login: true
                }
            }
        ]
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
        redirect: {//重定向设置 从定向到home
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
