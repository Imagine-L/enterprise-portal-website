package top.liubaiblog.masterstudiofront.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import top.liubaiblog.masterstudiofront.domain.po.Article;
import top.liubaiblog.masterstudiofront.domain.query.GetNoticeQuery;
import top.liubaiblog.masterstudiofront.domain.vo.PageVO;
import top.liubaiblog.masterstudiofront.domain.vo.article.ArticleDetailVO;
import top.liubaiblog.masterstudiofront.domain.vo.article.ArticlePartVO;
import top.liubaiblog.masterstudiofront.service.ArticleService;
import top.liubaiblog.masterstudiofront.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 13326
 * @description 针对表【ms_article(文章表)】的数据库操作Service实现
 * @createDate 2022-11-06 19:54:44
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Override
    public List<ArticlePartVO> getByNavBinding(Long nid) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Article::getAid,
                Article::getTitle,
                Article::getSummary,
                Article::getCreateTime);
        // 条件封装
        wrapper.eq(Article::getNavId, nid);
        wrapper.eq(Article::getReleased, true);
        wrapper.eq(Article::getDisabled, false);
        wrapper.orderByAsc(Article::getOrderSeed);
        // 查询
        List<Article> list = list(wrapper);
        // 返回值封装
        return list.stream().map(article -> {
            ArticlePartVO articlePartVO = new ArticlePartVO();
            BeanUtils.copyProperties(article, articlePartVO);
            return articlePartVO;
        }).collect(Collectors.toList());
    }

    @Override
    public ArticleDetailVO getDetailById(Long aid) {
        return getBaseMapper().selectDetailById(aid);
    }

    @Override
    public List<String> listNoticeDate() {
        return getBaseMapper().selectNoticeDate();
    }

    @Override
    public PageVO<ArticlePartVO> listNoticeByQuery(GetNoticeQuery noticeQuery) {
        // 合法性判断
        if (Objects.isNull(noticeQuery.getPageNo())) {
            noticeQuery.setPageNo(1);
        }
        if (Objects.isNull(noticeQuery.getPageSize()) ||
                noticeQuery.getPageSize() > 100) {
            noticeQuery.setPageSize(10);
        }
        // 这里这样做的目的是如果用户传递的是多个空格的字符串，也会被hasText()认为非法，我们就将多个空格消掉
        if (!StringUtils.hasText(noticeQuery.getTitle())) {
            noticeQuery.setTitle("");
        }
        // 查询
        Page<ArticlePartVO> page = new Page<>(noticeQuery.getPageNo(), noticeQuery.getPageSize());
        getBaseMapper().pageNoticeListByDate(page, noticeQuery.getDate(), noticeQuery.getTitle());
        // 封装返回值
        PageVO<ArticlePartVO> pageVO = new PageVO<>();
        pageVO.setTotalRecords(page.getTotal());
        pageVO.setTotalPages(page.getPages());
        pageVO.setRecords(page.getRecords());
        return pageVO;
    }
}




