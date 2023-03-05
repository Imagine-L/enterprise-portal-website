package top.liubaiblog.masterstudiofront.service;

import top.liubaiblog.masterstudiofront.domain.po.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import top.liubaiblog.masterstudiofront.domain.query.GetNoticeQuery;
import top.liubaiblog.masterstudiofront.domain.vo.PageVO;
import top.liubaiblog.masterstudiofront.domain.vo.article.ArticleDetailVO;
import top.liubaiblog.masterstudiofront.domain.vo.article.ArticlePartVO;

import java.util.List;

/**
* @author 13326
* @description 针对表【ms_article(文章表)】的数据库操作Service
* @createDate 2022-11-06 19:54:44
*/
public interface ArticleService extends IService<Article> {
    /**
     * 获取绑定指定栏目的文章 (这里不会检查栏目类型)
     */
    List<ArticlePartVO> getByNavBinding(Long nid);

    /**
     * 根据编号查询文章详情
     */
    ArticleDetailVO getDetailById(Long aid);

    /**
     * 获取通知日期列表
     */
    List<String> listNoticeDate();

    /**
     * 根据日期获取通知列表
     */
    PageVO<ArticlePartVO> listNoticeByQuery(GetNoticeQuery noticeQuery);
}
