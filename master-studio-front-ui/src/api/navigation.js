import request from "@/api/index";

// 获取栏目列表
export function getNavList() {
    return request({
        url: `/nav/header`,
        method: 'get'
    })
}

// 根据访问路径获取某个栏目的信息
export function getNavByPath(path) {
    return request({
        url: `/nav/${path}`,
        method: 'get'
    })
}

// 根据访问路径获取某个栏目的所有子板块
export function children(path) {
    return request({
        url: `/nav/${path}/children`,
        method: 'get'
    })
}

// 获取展示的栏目
export function getShowedNav() {
    return request({
        url: `/nav/list/show`,
        method: 'get'
    })
}
