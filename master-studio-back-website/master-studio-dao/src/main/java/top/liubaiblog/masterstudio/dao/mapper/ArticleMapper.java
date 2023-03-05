package top.liubaiblog.masterstudio.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.liubaiblog.masterstudio.domain.po.Article;
import top.liubaiblog.masterstudio.domain.query.article.GetArticleQuery;
import top.liubaiblog.masterstudio.domain.vo.article.ArticleDetailVO;
import top.liubaiblog.masterstudio.domain.vo.article.ArticlePartVO;

/**
* @author 13326
* @description 针对表【ms_article(文章表)】的数据库操作Mapper
* @createDate 2022-10-03 15:23:40
* @Entity top.liubaiblog.masterstudio.domain.do.Article
*/
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 根据条件查询文章页面
     */
    Page<ArticlePartVO> pageByCondition(Page<ArticlePartVO> page,
                                           @Param("title") String title,
                                           @Param("author") String author, @Param("navId") Long navId);

    ArticleDetailVO selectDetailById(@Param("aid") Long aid);
}




