import axios from 'axios';
import config from './config';
import apiURL from './apiUrls';

/**
 * 请求类
 */
class HttpRequest {
    constructor(options) {
        this.default = {
            baseURL: ''
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
        console.log(options)
        const instance = axios.create(options);
        this.interceptors(instance);
        return instance;
    }

    static createRequest() {
        return new HttpRequest();
    }
}


const http = HttpRequest.createRequest().request(config);

//获取首页banner数据
async function getBanner() {
    console.log(apiURL);
    return await http.get(apiURL.banner);
}

export {
    getBanner
}

// 获取所有属性分类
export async function getProperty(){
    return await http.get('/menu/property');
}
// 获取所有菜谱分类
export async function getClassify(){
    return await http.get('/menu/classify');
}

/**
 * 发布菜谱
 * @export
 * @param {Object} params - 需要上传的数据，参考backData
 * @returns
 */
export async function publish(params){
    return await http.post('/menu/publish', params);
}

/**
 * 注册账号
 * @export
 * @param {Object} params -
 * @param {Object} params.name - 用户名
 * @param {Object} params.password - 密码
 * @returns
 */
export async function register(params){
    return await http.post('/user/create', params);
}

/**
 * 账号登录
 * @export
 * @param {Object} params -
 * @param {string} params.name - 用户名
 * @param {string} params.password - 密码
 * @returns
 */
export async function login(params){
    return await http.post('/user/login', params);
}

/**
 * 账号登出
 */
export async function login_out(){
    return await http.post('/user/login_out');
}

/**
 * 获取用户信息
 * @export
 * @param {Object} params -
 * @param {string} [params.userId] - 用户id
 * @returns
 */
export async function userInfo(params){
    return await http.post('/user/info', params);
}

/**
 * 拿到用户发布的菜谱，可做筛使用选
 * @export
 * @param {Object} [params] - 不填写，则获取所有的菜谱
 * @param {string} [params.userId] - 指定用户的菜单
 * @param {string} [params.classify] - 按照菜单分类，进行筛选
 * @param {string} [params.property] - 指定菜单属性进行筛选
 * @param {string} [params.property.craft] - 按工艺筛选
 * @param {string} [params.property.flavor] - 按口味筛选
 * @param {string} [params.property.hard] - 按难度筛选
 * @param {string} [params.property.people] - 按人数筛选
 * @param {string} [params.page] - 指定页码
 * @returns
 */
export async function getMenus(params){
    return await http.get('/menu/query', {params});
}

/**
 * 根据菜单id，拿到菜谱的详细信息
 * @export
 * @param {Object} [params] -
 * @param {string} [params.menuId] - 指定菜单的id
 * @returns
 */
export async function menuInfo(params){
    return await http.get('/menu/menuInfo', {params});
}

/**
 * toggle 收藏。收藏的取消收藏；没收藏的，要收藏。
 * @export
 * @param {Object} [params] -
 * @param {string} [params.menuId] - 指定要收藏的菜单的id
 * @returns
 */
export async function toggleCollection(params){
    return await http.post('/user/collection', params);
}
/**
 * toggle 关注。关注的取消关注；没关注的，要关注。
 * @export
 * @param {Object} [params] -
 * @param {string} [params.followUserId] - 指定要关注的用户的id
 * @returns
 */
export async function toggleFollowing(params){
    return await http.post('/user/following', params);
}

/**
 * 获取指定用户的收藏的菜单
 * @export
 * @param {Object} [params] -
 * @param {string} [params.userId] - 指定的用户id
 * @returns
 */
export async function collection(params){
    return await http.get('/user/collection', {params});
}

/**
 * 获取指定用户关注的人
 * @export
 * @param {Object} [params] -
 * @param {string} [params.userId] - 指定的用户id
 * @returns
 */
export async function following(params){
    return await http.get('/user/following', {params});
}

/**
 * 获取指定用户的粉丝
 * @export
 * @param {Object} [params] -
 * @param {string} [params.userId] - 指定的用户id
 * @returns
 */
export async function fans(params){
    return await http.get('/user/fans', {params});
}

/**
 * 上传图片
 * @export
 * @param {Object} [params] -
 * @param {string} [params.type] - product user step
 * @returns
 */
export async function upload(params){
    return await http.post('/upload', {params});
}

/**
 * 修改用户信息
 * @export
 * @param {Object} [params] -
 * @param {string} [params.name] - product user step
 * @param {string} [params.avatar] - product user step
 * @param {string} [params.sign] - product user step
 * @returns
 */
export async function userEdit(params){
    return await http.post('/user/edit', params);
}


/**
 * 获取菜谱评论信息
 * @export
 * @param {Object} [params] -
 * @param {string} [params.menuId] - 指定菜谱的id
 * @returns
 */
export async function getComments(params){
    return await http.get('/menu/comment', {params});
}
/**
 * 提交菜谱评论信息
 * @export
 * @param {Object} [params] -
 * @param {string} [params.menuId] - 指定评论的菜谱id
 * @param {string} [params.commentText] - 评论内容
 * @returns
 */
export async function postComment(params){
    return await http.post('/menu/comment', params);
}
