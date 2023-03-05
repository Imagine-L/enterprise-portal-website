import request from "@/api/index";

// 获取轮播图列表
export function getCarouselList() {
    return request({
        url: `/chart/list`,
        method: 'get'
    })
}
