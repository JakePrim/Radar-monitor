import axios from 'axios'

const BASE_URL = "/api"
const API_URL = {
    login: {
        admin: BASE_URL + "/admin/login",
        common: BASE_URL + "/login"
    },
    register: `${BASE_URL}/register`,
    item: {
        getItems: BASE_URL + '/items',
        getItem: BASE_URL + '/item'
    }
}

export default {
    async login(options) {
        let param = new URLSearchParams()
        param.append('name', options.name)
        param.append('password', options.password)
        return await axios({
            url: API_URL.login.common,
            method: 'post',
            data: param
        });
    },
    async register(options) {
        let param = new URLSearchParams()
        param.append('name', options.name)
        param.append('password', options.password)
        param.append('repassword', options.password)
        return await axios({
            url: API_URL.register,
            method: 'post',
            data: options
        })
    },
    async getItems(params={
        page:1,
        prepage:10
    }) {
        return await axios({
            url: API_URL.item.getItems,
            params
        });
    },
    async getItem(id){
        return await axios({
            url:API_URL.item.getItem+"/"+id
        });
    }
}