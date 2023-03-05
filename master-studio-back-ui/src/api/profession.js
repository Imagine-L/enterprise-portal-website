import request from "@/api/index";

// 获取岗位名列表
export function getProfessionNames() {
    return request({
        url: '/profession/names',
        method: 'get',
    })
}

// 获取岗位列表
export function getProfessionList(data) {
    return request({
        url: `/profession/list`,
        method: 'post',
        data: data
    })
}

// 获取岗位详细信息
export function getProfessionDetail(pid) {
    return request({
        url: `/profession/${pid}`,
        method: 'get'
    })
}

// 新增岗位
export function saveProfession(data) {
    return request({
        url: `/profession`,
        method: 'post',
        data: data
    })
}

// 判断岗位名是否重复
export function checkProfessionName(name) {
    return request({
        url: `/profession/name/${name}`,
        method: 'get'
    })
}

// 修改岗位操作
export function updateProfession(data) {
    return request({
        url: `/profession`,
        method: 'put',
        data: data
    })
}

// 删除岗位操作
export function deleteProfession(pid) {
    return request({
        url: `/profession/${pid}`,
        method: 'delete'
    })
}
