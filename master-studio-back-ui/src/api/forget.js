import request from "@/api/index";

// 发送验证码
export function sendCode(username) {
    return request({
        url: `/forget/${username}`,
        method: 'get'
    })
}

// 判断验证码是否正确
export function validateCode(data) {
    return request({
        url: `/forget`,
        method: 'post',
        data: data
    })
}

// 根据临时令牌修改密码
export function updatePwdByToken(data) {
    return request({
        url: `/forget`,
        method: 'put',
        data: data
    })
}
