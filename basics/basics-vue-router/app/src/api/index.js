import axios from 'axios'

const BASE_URL = "/api"
const API_URL = {
    login: {
        admin: BASE_URL + "/admin/login",
        common: BASE_URL + "/login"
    },
    register: `${BASE_URL}/register`,
    item: {
        getItems: BASE_URL + '/items'
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
    async getItems() {
        return await axios({
            url: API_URL.item.getItems
        });
    }
}