import request from "@/api/index";

// 获取栏目列表
export function getNavList(data) {
    return request({
        url: '/nav/list',
        method: 'post',
        data: data
    })
}

// 获取栏目详情
export function getNavDetail(nid) {
    return request({
        url: `/nav/${nid}`,
        method: 'get',
    })
}

// 获取某个等级的栏目名列表
export function getNavNamesByLevel(level) {
    return request({
        url: `/nav/level/${level}`,
        method: 'get',
    })
}

// 判断栏目名是否重复
export function existName(navName) {
    return request({
        url: `/nav/name/${navName}`,
        method: 'get',
    })
}

// 判断栏目名是否重复
export function existPath(path) {
    return request({
        url: `/nav/path/${path}`,
        method: 'get',
    })
}

// 保存栏目
export function saveNavigation(data) {
    return request({
        url: `/nav`,
        method: 'post',
        data: data,
        headers: {'Content-type' : 'multipart/form-data'}
    })
}

// 修改栏目
export function updateNavigation(data) {
    return request({
        url: `/nav`,
        method: 'put',
        data: data,
        headers: {'Content-type' : 'multipart/form-data'}
    })
}

// 删除栏目
export function deleteNavigation(nid) {
    return request({
        url: `/nav/${nid}`,
        method: 'delete',
    })
}

// 获取某个类别的多级栏目信息
export function multiNavigation(navType) {
    return request({
        url: `/nav/multi/${navType}`,
        method: 'get',
    })
}
