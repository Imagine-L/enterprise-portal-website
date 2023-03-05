import request from "@/api/index";

// 获取列表
export function getFileList(data) {
    return request({
        url: '/file/list',
        method: 'post',
        data: data
    })
}

// 获取某个文件的详细信息
export function getFileDetail(fid) {
    return request({
        url: `/file/${fid}`,
        method: 'get',
    })
}

// 删除文件
export function deleteFile(fid) {
    return request({
        url: `/file/${fid}`,
        method: 'delete',
    })
}

// 下载文件
export function downloadFile(fid) {
    return request({
        url: `/file/download/${fid}`,
        method: 'get',
        responseType: 'blob',
    })
}
