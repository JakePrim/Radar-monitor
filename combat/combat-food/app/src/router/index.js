import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/home/Home.vue'
import {userInfo} from '@/service/request'
import Store from "@/store";

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

/**
 * 清空token方法
 */
function extracted() {
    localStorage.removeItem('token');
}

router.beforeEach(async (to, form, next) => {
    //to目标路由
    const token = localStorage.getItem('token');
    //每一次进入路由的时候都要向后端发送token 验证是否合法 防止用户自己输入token导致 错误
    //不管路由需不需要登录 都需要向后端发送请求 拿到用户信息 判断token是否正确合法
    const data = await userInfo();
    //设置数据
    Store.commit('changeUserInfo', data.data);
    const isLogin = !!token;//标记用户是否登录
    console.log(isLogin, data)


    if (to.matched.some(item => item.meta.login)) {
        //只要有一个需要登录就需要登录
        if (isLogin) {//如果已经标记了用户登录的状态
            //如果token不合法 后台会返回400 这时候跳转到登录页面 并清空token
            if (data.error === 400) {
                //后端告诉你 登录没成功
                next({name: 'login'})
                //清空token
                extracted();
                return;
            } else if (data.code !== 0) {
                //表示网络错误 或者根据token请求的用户不存在
                //跳转到登录页
                next({name: 'login'});
                //清空token
                extracted();
                return;
            }
            //如果用户已经登录了 并且请求login页那么直接跳转到首页
            if (to.name === 'login') {
                next({name: 'home'})
            } else {
                next();
            }
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
});

export default router
