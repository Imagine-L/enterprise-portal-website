import request from "@/api/index";

// 获取轮播图列表
export function getChartList() {
    return request({
        url: `/chart/list`,
        method: 'get'
    })
}

// 添加轮播图
export function saveChart(data) {
    return request({
        url: `/chart`,
        method: 'post',
        data: data,
        headers: {'Content-type' : 'multipart/form-data'}
    })
}

// 修改轮播图
export function updateChart(data) {
    return request({
        url: `/chart`,
        method: 'put',
        data: data,
        headers: {'Content-type' : 'multipart/form-data'}
    })
}

// 删除轮播图
export function deleteChart(hid) {
    return request({
        url: `/chart/${hid}`,
        method: 'delete',
    })
}

