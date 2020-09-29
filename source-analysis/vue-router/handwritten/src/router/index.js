import VueRouter from '@/vuerouter'
import Vue from 'vue';
import Home from '@/components/Home'

Vue.use(VueRouter);

const routes = [
    {
        name: 'home',
        path: '/',
        component: Home
    },
    {
        name: 'about',
        path: "/about",
        component: () => import('@/components/About')
    }
]

const router = new VueRouter({
    mode: 'history',
    routes
});

export default router;