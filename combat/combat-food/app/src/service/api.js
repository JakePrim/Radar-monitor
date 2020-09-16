import axios from 'axios';

class HttpRequest {
    constructor(options) {
        this.default = {
            baseUrl: ''
        };
        this.default = Object.assign(this.default, options);
    }

    setConfig() {

    }

    interceptors(install) {
        //install axios的实例
        //配置请求拦截器
        install.interceptors.request.use(config => {
            //配置token
            let token = localStorage.getItem("token");
            if (token) {
                config.headers.authorization = `token ${token}`;
            }
            return config;
        }, error => {
            return Promise.reject(error);
        });
        //配置响应拦截器
        install.interceptors.response.use(res => {
            const {data} = res;
            return data;
        }, error => {
            return Promise.reject(error);
        })
    }

    request(options) {
        options = Object.assign(this.default, options);
        const instance = axios.create(options);
        this.interceptors(instance);
        return instance;
    }

    static createRequest(options) {
        return new HttpRequest(options);
    }
}


const http = HttpRequest.createRequest({
    baseUrl: '/api'
}).request();

//获取首页banner数据
async function getBanner() {
    return await http.get('banner');
}

export {
    getBanner
}
