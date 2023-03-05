import request from "@/api/index";

// 获取文章详情
export function getArticleDetail(aid) {
    return request({
        url: `/article/${aid}`,
        method: 'get'
    })
}

// 获取通知日期列表
export function getNoticeDateList() {
    return request({
        url: `/article/notice/date`,
        method: 'get'
    })
}

// 通过日期获取通知列表
export function getNoticeListByDate(data) {
    return request({
        url: `/article/notice/list`,
        method: 'post',
        data: data
    })
}

