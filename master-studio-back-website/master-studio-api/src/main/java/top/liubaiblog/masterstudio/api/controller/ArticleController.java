package top.liubaiblog.masterstudio.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.liubaiblog.masterstudio.common.annotation.SetLog;
import top.liubaiblog.masterstudio.domain.enums.LogModuleEnums;
import top.liubaiblog.masterstudio.domain.enums.ResponseDataEnum;
import top.liubaiblog.masterstudio.domain.query.article.GetArticleQuery;
import top.liubaiblog.masterstudio.domain.query.article.SaveArticleQuery;
import top.liubaiblog.masterstudio.domain.query.article.UpdateArticleQuery;
import top.liubaiblog.masterstudio.domain.vo.PageVO;
import top.liubaiblog.masterstudio.domain.vo.ResponseData;
import top.liubaiblog.masterstudio.domain.vo.article.ArticleDetailVO;
import top.liubaiblog.masterstudio.domain.vo.article.ArticlePartVO;
import top.liubaiblog.masterstudio.domain.vo.wangeditor.EditorResponse;
import top.liubaiblog.masterstudio.domain.vo.wangeditor.image.FileData;
import top.liubaiblog.masterstudio.service.ArticleService;
import top.liubaiblog.masterstudio.util.http.ResponseDataBuilder;

/**
 * @author 留白
 * @description
 */
@RestController
@Api(tags = "文章发布控制器")
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation("获取文章列表")
    @PostMapping("/list")
    public ResponseData<PageVO<ArticlePartVO>> list(@RequestBody @Validated GetArticleQuery articleQuery) {
        PageVO<ArticlePartVO> articleVOs = articleService.listByArticleQuery(articleQuery);
        return ResponseDataBuilder.builder().success(articleVOs);
    }

    @ApiOperation("查看文章详情")
    @GetMapping("/{aid}")
    public ResponseData<ArticleDetailVO> getById(@PathVariable("aid") Long aid) {
        ArticleDetailVO articleDetailVO = articleService.getById(aid);
        return ResponseDataBuilder.builder().success(articleDetailVO);
    }

    @ApiOperation("保存文章")
    @PostMapping
    @SetLog(module = LogModuleEnums.ARTICLE, operType = "保存文章")
    public ResponseData<Void> save(@RequestBody @Validated SaveArticleQuery articleQuery) {
        if (articleService.saveByArticleQuery(articleQuery)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

    @ApiOperation("修改文章")
    @PutMapping
    @SetLog(module = LogModuleEnums.ARTICLE, operType = "修改文章")
    public ResponseData<Void> update(@RequestBody @Validated UpdateArticleQuery articleQuery) {
        if (articleService.updateByArticleQuery(articleQuery)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

    @ApiOperation("删除文章")
    @DeleteMapping("/{aid}")
    @SetLog(module = LogModuleEnums.ARTICLE, operType = "删除文章")
    public ResponseData<Void> removeById(@PathVariable("aid") Long aid) {
        if (articleService.removeById(aid)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

    @ApiOperation("保存文章的图片和视频")
    @PostMapping("/file")
    public EditorResponse<FileData> saveFile(@RequestPart("file") MultipartFile file) {
        FileData fileData = this.articleService.saveFile(file);
        return (EditorResponse) EditorResponse.ok(fileData);
    }
}
