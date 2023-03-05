import axios from "axios";
import store from "@/store";
import router from "@/route";
import {userMutations} from "@/constant";
import config from "@/config/config";

// 创建 axios 实例
const request = axios.create({
    // 请求url的公共部分
    // baseURL: 'http://localhost:5050',
    baseURL: config.BASE_URL,
    // 超时时间
    timeout: 5000,
    // 设置 `content-type` ，规定前后端json格式通信
    headers: {'Content-Type': 'application/json;charset=UTF-8'}
})

// 请求拦截器，自动注入token
request.interceptors.request.use(config => {
    // 获取token
    let token = store.getters.token;
    if (token !== null && token !== '') {
        // 如果用户含有token，则每次请求携带token
        config.headers['Authorization'] = token
    }
    return config
}, error => {
    // 对请求错误做写什么
    return Promise.reject(error)
})

// 响应拦截器
request.interceptors.response.use(resp => {
    // 如果出现未登录异常则直接退出到登录页
    if (!resp.data.success && resp.data.status === 'A0200') {
        router.push({name: 'login'})
        store.commit(userMutations.REMOVE_LOCAL_TOKEN)
    }
    // 只要data域的数据
    return resp.data;
}, err => {
    console.log(err);
})

export default request
