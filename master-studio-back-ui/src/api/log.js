import request from "@/api/index";

// 查询日志列表
export function getLogList(data) {
    return request({
        url: '/log/list',
        method: 'post',
        data: data
    })
}

// 删除日志列表
export function deleteLogList(data) {
    return request({
        url: '/log',
        method: 'delete',
        data: data
    })
}

// 删除全部日志
export function deleteLogAll() {
    return request({
        url: '/log/all',
        method: 'delete'
    })
}

// 查看日志详情
export function getLogDetail(lid) {
    return request({
        url: `/log/${lid}`,
        method: 'get'
    })
}
