import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        //需要管理的数据
        userInfo: {}
    },
    getters: {
        //标记是否登录
        isLogin(state) {
            return !!Object.keys(state.userInfo).length
        }
    },
    mutations: {
        changeUserInfo(state, data) {
            console.log(data);
            //判断是否是合法的数据
            state.userInfo = data;
        }
    },
    actions: {
        //触发状态变更的动作
    }
});

export default store;
