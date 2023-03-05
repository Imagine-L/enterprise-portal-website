package top.liubaiblog.masterstudio.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.liubaiblog.masterstudio.common.annotation.SetLog;
import top.liubaiblog.masterstudio.domain.enums.LogModuleEnums;
import top.liubaiblog.masterstudio.domain.query.file.GetFileQuery;
import top.liubaiblog.masterstudio.domain.vo.PageVO;
import top.liubaiblog.masterstudio.domain.vo.ResponseData;
import top.liubaiblog.masterstudio.domain.enums.ResponseDataEnum;
import top.liubaiblog.masterstudio.domain.vo.file.FileDetailVO;
import top.liubaiblog.masterstudio.domain.vo.file.FilePartVO;
import top.liubaiblog.masterstudio.domain.vo.file.SaveFileVO;
import top.liubaiblog.masterstudio.service.UploadFileService;
import top.liubaiblog.masterstudio.util.http.ResponseDataBuilder;

/**
 * @author 留白
 * @description
 */
@RestController
@RequestMapping("/file")
@Api(tags = "文件管理控制器")
public class UploadFileController {

    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/list")
    @ApiOperation("获取文件列表")
    public ResponseData<PageVO<FilePartVO>> list(@RequestBody @Validated GetFileQuery fileQuery) {
        PageVO<FilePartVO> page = uploadFileService.listByFileQuery(fileQuery);
        return ResponseDataBuilder.builder().success(page);
    }

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public ResponseData<SaveFileVO> save(@RequestPart MultipartFile file) {
        SaveFileVO fileVO = uploadFileService.save(file);
        return ResponseDataBuilder.builder().success(fileVO);
    }

    @GetMapping("/{fid}")
    @ApiOperation("获取某个文件的信息")
    public ResponseData<FileDetailVO> getById(@PathVariable("fid") Long fid) {
        FileDetailVO fileDetailVO = uploadFileService.getById(fid);
        return ResponseDataBuilder.builder().success(fileDetailVO);
    }

    @DeleteMapping("/{fid}")
    @ApiOperation("删除文件")
    @SetLog(module = LogModuleEnums.FILE, operType = "删除文件")
    public ResponseData<Void> delete(@PathVariable("fid") Long fid) {
        boolean result = uploadFileService.removeById(fid);
        if (result) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

    @GetMapping("/download/{fid}")
    public void downloadFile(@PathVariable("fid") Long fid) {
        uploadFileService.downloadFile(fid);
    }

}
