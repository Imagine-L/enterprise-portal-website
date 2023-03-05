import request from "@/api/index";

// 测试方法
export function test(data) {
    return request({
        url: '/test',
        method: 'get',
        data: data
    })
}

// 登录方法
export function login(data) {
    return request({
        url: '/login',
        method: 'post',
        data: data
    })
}

// 登出方法
export function logout() {
    return request({
        url: '/logout',
        method: 'get'
    })
}
