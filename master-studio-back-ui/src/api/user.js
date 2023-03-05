import request from "@/api/index";

// 查询用户详细信息
export function getUserDetail(uid) {
    return request({
        url: `/user/${uid}`,
        method: 'get',
    })
}

// 查询来源信息
export function getUserSource() {
    return new Promise((resolve,reject)=>{
        let script = document.createElement('script');
        script.type = "text/javascript";
        script.src= 'http://pv.sohu.com/cityjson?ie=utf-8';
        document.body.appendChild(script);
        script.onload = ()=>{
            resolve();
        }
        script.onerror = ()=>{
            reject();
        }
    })
}

// 查看用户列表
export function getUserList(data) {
    return request({
        url: `/user/list`,
        method: 'post',
        data: data
    })
}

// 修改用户信息
export function updateUser(data) {
    return request({
        url: `/user`,
        method: 'put',
        data: data
    })
}

// 修改用户密码
export function updateUserPwd(data) {
    return request({
        url: `/user/pwd`,
        method: 'put',
        data: data
    })
}

// 删除用户
export function deleteUser(uid) {
    return request({
        url: `/user/${uid}`,
        method: 'delete',
    })
}

// 判断用户名是否重复
export function checkUsername(username) {
    return request({
        url: `/user/name/${username}`,
        method: 'get',
    })
}

// 保存用户信息
export function saveUser(data) {
    return request({
        url: `/user`,
        method: 'post',
        data: data
    })
}

// 判断邮箱是否已经绑定
export function emailIsBinding(email) {
    return request({
        url: `/user/email/${email}`,
        method: 'post'
    })
}
