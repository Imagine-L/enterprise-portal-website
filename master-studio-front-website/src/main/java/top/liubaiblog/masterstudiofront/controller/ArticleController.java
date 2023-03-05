package top.liubaiblog.masterstudiofront.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.liubaiblog.masterstudiofront.domain.query.GetNoticeQuery;
import top.liubaiblog.masterstudiofront.domain.vo.PageVO;
import top.liubaiblog.masterstudiofront.domain.vo.ResponseData;
import top.liubaiblog.masterstudiofront.domain.vo.article.ArticleDetailVO;
import top.liubaiblog.masterstudiofront.domain.vo.article.ArticlePartVO;
import top.liubaiblog.masterstudiofront.service.ArticleService;
import top.liubaiblog.masterstudiofront.util.http.ResponseDataBuilder;

import java.util.List;

/**
 * @author 留白
 * @description
 */
@RestController
@RequestMapping("/article")
@Api(tags = "文章控制器")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/{aid}")
    @ApiOperation("获取文章正文内容")
    public ResponseData<ArticleDetailVO> getDetailById(@PathVariable("aid") Long aid) {
        ArticleDetailVO articleDetailVO = articleService.getDetailById(aid);
        return ResponseDataBuilder.builder().success(articleDetailVO);
    }

    @GetMapping("/notice/date")
    @ApiOperation("获取通知日期归纳列表")
    public ResponseData<List<String>> getNoticeDateList() {
        List<String> dates = articleService.listNoticeDate();
        return ResponseDataBuilder.builder().success(dates);
    }

    @PostMapping("/notice/list")
    @ApiOperation("根据日期获取通知列表")
    public ResponseData<PageVO<ArticlePartVO>>
    listNoticeByDate(@RequestBody GetNoticeQuery noticeQuery) {
        PageVO<ArticlePartVO> notices = articleService.listNoticeByQuery(noticeQuery);
        return ResponseDataBuilder.builder().success(notices);
    }

}
