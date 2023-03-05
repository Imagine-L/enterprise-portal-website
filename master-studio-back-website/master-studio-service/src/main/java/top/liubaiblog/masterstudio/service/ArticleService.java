package top.liubaiblog.masterstudio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import top.liubaiblog.masterstudio.domain.po.Article;
import top.liubaiblog.masterstudio.domain.query.article.GetArticleQuery;
import top.liubaiblog.masterstudio.domain.query.article.SaveArticleQuery;
import top.liubaiblog.masterstudio.domain.query.article.UpdateArticleQuery;
import top.liubaiblog.masterstudio.domain.vo.PageVO;
import top.liubaiblog.masterstudio.domain.vo.article.ArticleDetailVO;
import top.liubaiblog.masterstudio.domain.vo.article.ArticlePartVO;
import top.liubaiblog.masterstudio.domain.vo.wangeditor.image.FileData;

import java.util.List;

/**
* @author 13326
* @description 针对表【ms_article(文章表)】的数据库操作Service
* @createDate 2022-10-03 15:23:40
*/
public interface ArticleService extends IService<Article> {

    /**
     * 获取绑定指定栏目的文章 (这里不会检查栏目类型)
     */
    List<Article> getByNavBinding(Long nid);

    /**
     * 根据条件获取文章
     */
    PageVO<ArticlePartVO> listByArticleQuery(GetArticleQuery articleQuery);

    /**
     * 根据编号获取文章详情
     */
    ArticleDetailVO getById(Long aid);

    /**
     * 根据条件保存文章
     */
    boolean saveByArticleQuery(SaveArticleQuery articleQuery);

    /**
     * 保存文章的图片和视频
     */
    FileData saveFile(MultipartFile file);

    /**
     * 根据条件修改文章
     */
    boolean updateByArticleQuery(UpdateArticleQuery articleQuery);

    /**
     * 根据编号删除文章
     */
    boolean removeById(Long aid);
}
