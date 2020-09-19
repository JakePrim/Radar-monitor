import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/home/Home.vue'


Vue.use(VueRouter)

const MenuList = () => import(/* webpackChunkName: "space" */ '@/views/user/menu-list');
const Space = () => import(/* webpackChunkName: "space" */ '@/views/user/space');
const Funs = () => import(/* webpackChunkName: "space" */ '@/views/user/fans');

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
        component: () => import(/* webpackChunkName: "login" */ '@/views/login/index'),
        meta: {//是否登录标记
            login: true,
        }
    },
    {
        path: '/detail',
        name: 'detail',
        title: '菜谱详情',
        component: () => import(/* webpackChunkName: "detail" */ '@/views/detail/Detail')
    },
    {
        path: '/space',
        title: '个人空间',
        name: 'space',
        component: Space,
        redirect: {
            name: 'works'//该组件下有多个组件 默认显示的组件名
        },
        meta: {//用来标记需要登录
            login: true,
        },
        children: [
            {
                path: 'works',
                name: 'works',
                title: '作品',
                component: MenuList,
                meta: {
                    login: true
                }
            },
            {
                path: 'fans',
                name: 'fans',
                title: '粉丝',
                component: Funs,
                meta: {
                    login: true
                }
            },
            {
                path: 'following',
                name: 'following',
                title: '关注',
                component: Funs,
                meta: {
                    login: true
                }
            },
            {
                path: 'collection',
                name: 'collection',
                title: '收藏',
                component: MenuList
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

router.beforeEach((to, form, next) => {
    //to目标路由
    console.log('??to:', to);
    const isLogin = false;//标记用户是否登录
    if (to.matched.some(item => item.meta.login)) {
        //只要有一个需要登录就需要登录
        if (isLogin) {
            next();
            return;
        }
        //没有登录，进入login直接进入
        if (!isLogin && to.name === 'login') {
            next();
        } else {
            //没有登录，进入的不是login 跳转到login
            next({name: 'login'});
        }
    } else {
        //如果要进入路由必须调用next
        next();
    }


})

export default router
