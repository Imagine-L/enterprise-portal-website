import request from "@/api/index";

// 获取文章列表
export function getArticleList(data) {
    return request({
        url: '/article/list',
        method: 'post',
        data: data
    })
}

// 获取文章详情
export function getArticleDetail(aid) {
    return request({
        url: `/article/${aid}`,
        method: 'get',
    })
}

// 保存文章
export function saveArticle(data) {
    return request({
        url: `/article`,
        method: 'post',
        data: data
    })
}

// 修改文章
export function updateArticle(data) {
    return request({
        url: `/article`,
        method: 'put',
        data: data
    })
}

// 删除文章
export function deleteArticle(aid) {
    return request({
        url: `/article/${aid}`,
        method: 'delete',
    })
}
