package top.liubaiblog.masterstudiofront.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.liubaiblog.masterstudiofront.domain.po.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.liubaiblog.masterstudiofront.domain.vo.article.ArticleDetailVO;
import top.liubaiblog.masterstudiofront.domain.vo.article.ArticlePartVO;

import java.util.List;

/**
* @author 13326
* @description 针对表【ms_article(文章表)】的数据库操作Mapper
* @createDate 2022-11-06 19:54:44
* @Entity top.liubaiblog.masterstudiofront.domain.po.Article
*/
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 根据编号查询文章详情
     */
    ArticleDetailVO selectDetailById(@Param("aid") Long aid);

    /**
     * 获取通知日期列表
     */
    List<String> selectNoticeDate();

    /**
     * 根据日期获取通知列表
     */
    Page<ArticlePartVO> pageNoticeListByDate(Page<ArticlePartVO> page,
                                             @Param("date") String date,
                                             @Param("title") String title);
}




