package top.liubaiblog.masterstudio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import top.liubaiblog.masterstudio.domain.po.UploadFile;
import top.liubaiblog.masterstudio.domain.query.file.GetFileQuery;
import top.liubaiblog.masterstudio.domain.vo.PageVO;
import top.liubaiblog.masterstudio.domain.vo.file.FileDetailVO;
import top.liubaiblog.masterstudio.domain.vo.file.FilePartVO;
import top.liubaiblog.masterstudio.domain.vo.file.SaveFileVO;

/**
* @author 13326
* @description 针对表【ms_upload_file(文件表)】的数据库操作Service
* @createDate 2022-10-03 15:23:40
*/
public interface UploadFileService extends IService<UploadFile> {

    /**
     * 根据查询条件获取文件列表
     */
    PageVO<FilePartVO> listByFileQuery(GetFileQuery fileQuery);

    /**
     * 保存文件
     */
    SaveFileVO save(MultipartFile file);

    /**
     * 根据编号获取文件对象
     */
    FileDetailVO getById(Long fid);

    /**
     * 删除文件
     */
    boolean removeById(Long fid);

    /**
     * 获取文件本地路径
     */
    String getLocalPathById(Long fid);

    /**
     * 获取下载文件的byte数组
     */
    void downloadFile(Long fid);
}
