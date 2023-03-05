import request from "@/api/index";

// 获取板块列表
export function getPlateList(data) {
    return request({
        url: '/plate/list',
        method: 'post',
        data: data
    })
}

// 获取板块详情
export function getPlateDetail(pid) {
    return request({
        url: `/plate/${pid}`,
        method: 'get',
    })
}

// 保存板块
export function savePlate(data) {
    return request({
        url: `/plate`,
        method: 'post',
        data: data,
        headers: {'Content-type' : 'multipart/form-data'}
    })
}

// 更新板块
export function updatePlate(data) {
    return request({
        url: `/plate`,
        method: 'put',
        data: data,
        headers: {'Content-type' : 'multipart/form-data'}
    })
}

// 删除板块
export function deletePlate(pid) {
    return request({
        url: `/plate/${pid}`,
        method: 'delete',
    })
}
