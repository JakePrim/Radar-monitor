let _Vue = null;
export default class VueRouter {
    static install(Vue) {
        //1. 判断当前插件是否已经被安装
        if (VueRouter.install.installed) {
            return;
        }
        VueRouter.install.installed = true;
        //2. 把Vue构造函数记录到全局变量
        _Vue = Vue;
        //3. 把创建Vue实例时候传入的router对象注入到Vue实例上
        //混入
        _Vue.mixin({
            beforeCreate() {
                //在beforeCreate的生命周期中 添加$router
                if (this.$options.router) { //判断只添加到Vue的实例中
                    _Vue.prototype.$router = this.$options.router;
                    this.$options.router.init();
                }
            }
        })
    }

    constructor(options) {
        this.options = options;
        this.routeMap = {};//记录路由规则
        this.data = _Vue.observable({
            current: '/'// 存储当前的路由地址
        });//响应式对象
    }

    init() {
        this.createRouteMap();
        this.initComponents(_Vue);
        this.initEvent();
    }

    createRouteMap() {
        //解析路由 存储到routeMap
        this.options.routes.forEach(route => {
            this.routeMap[route.path] = route.component;
        })
    }

    initComponents(Vue) {
        //创建<router-link>组件
        Vue.component('router-link', {
            props: {
                to: String
            },
            render(h) {
                return h('a', {
                    attrs: {
                        href: this.to
                    },
                    //注册事件
                    on:{
                        click: this.clickHandler
                    }
                }, [this.$slots.default]);
            },
            methods:{
                clickHandler(e){
                    //调用pushstate方法改变地址栏 不会像服务器发送请求
                    history.pushState({},"",this.to);
                    //改变当前的路由地址 响应式的
                    this.$router.data.current = this.to;
                    e.preventDefault();
                }
            }
        });
        const self = this;//VueRouter实例
        //创建<router-view>组件
        Vue.component('router-view', {
            render(h) {
                const component = self.routeMap[self.data.current]
                return h(component);
            }
        })
    }

    /**
     * 注册popstate事件
     */
    initEvent(){
        //监听前进和后退事件
        window.addEventListener('popstate',()=>{
            this.data.current = window.location.pathname;
        })
    }
}