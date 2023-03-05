package top.liubaiblog.masterstudio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import top.liubaiblog.masterstudio.common.exception.param.RequestParamsException;
import top.liubaiblog.masterstudio.dao.mapper.ArticleMapper;
import top.liubaiblog.masterstudio.domain.enums.NavTypeEnums;
import top.liubaiblog.masterstudio.domain.po.Article;
import top.liubaiblog.masterstudio.domain.po.Navigation;
import top.liubaiblog.masterstudio.domain.po.User;
import top.liubaiblog.masterstudio.domain.query.article.GetArticleQuery;
import top.liubaiblog.masterstudio.domain.query.article.SaveArticleQuery;
import top.liubaiblog.masterstudio.domain.query.article.UpdateArticleQuery;
import top.liubaiblog.masterstudio.domain.vo.PageVO;
import top.liubaiblog.masterstudio.domain.vo.article.ArticleDetailVO;
import top.liubaiblog.masterstudio.domain.vo.article.ArticlePartVO;
import top.liubaiblog.masterstudio.domain.vo.file.SaveFileVO;
import top.liubaiblog.masterstudio.domain.vo.wangeditor.image.FileData;
import top.liubaiblog.masterstudio.service.ArticleService;
import top.liubaiblog.masterstudio.service.NavigationService;
import top.liubaiblog.masterstudio.service.UploadFileService;
import top.liubaiblog.masterstudio.service.UserService;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author 13326
* @description 针对表【ms_article(文章表)】的数据库操作Service实现
* @createDate 2022-10-03 15:23:40
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService {

    @Autowired
    private NavigationService navigationService;

    @Autowired
    private UploadFileService uploadFileService;

    @Override
    public List<Article> getByNavBinding(Long nid) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getNavId, nid);
        wrapper.orderByDesc(Article::getReleased, Article::getDisabled);
        return list(wrapper);
    }

    @Override
    public PageVO<ArticlePartVO> listByArticleQuery(GetArticleQuery articleQuery) {
        Page<ArticlePartVO> articlePage = new Page<>(articleQuery.getPageNo(), articleQuery.getPageSize());
        getBaseMapper().pageByCondition(articlePage, articleQuery.getTitle(),
                articleQuery.getAuthor(),
                articleQuery.getNavId());
        PageVO<ArticlePartVO> pageVO = new PageVO<>();
        pageVO.setTotalRecords(articlePage.getTotal());
        pageVO.setTotalPages(articlePage.getPages());
        pageVO.setRecords(articlePage.getRecords());
        return pageVO;
    }

    @Override
    public ArticleDetailVO getById(Long aid) {
        if (Objects.isNull(aid)) {
            throw new RequestParamsException("文章编号不能为空");
        }
        return getBaseMapper().selectDetailById(aid);
    }

    @Override
    public boolean saveByArticleQuery(SaveArticleQuery articleQuery) {
        // 判断绑定的栏目是否存在，并且为文章类型
        Navigation nav = navigationService.exist(articleQuery.getNavId());
        if (Objects.isNull(nav) || !Objects.equals(nav.getNavType(), NavTypeEnums.ARTICLE.getCode())) {
            throw new RequestParamsException("绑定的栏目不存在或绑定栏目非文章栏目");
        }
        // 获取登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        // 封装保存参数
        Article article = new Article();
        BeanUtils.copyProperties(articleQuery, article);
        article.setCreateBy(currentUser.getUid());
        article.setCreateTime(new Date());
        article.setUpdateBy(currentUser.getUid());
        article.setUpdateTime(new Date());
        // 保存栏目
        boolean result = save(article);
        if (!result) {
            return false;
        }
        // 如果文章未禁用，且已经发布，则需要更新栏目可用性
        if (!articleQuery.getDisabled() && articleQuery.getReleased()) {
            navigationService.adaptUsedById(articleQuery.getNavId());
        }
        return true;
    }

    @Override
    public boolean updateByArticleQuery(UpdateArticleQuery articleQuery) {
        // 判断绑定的栏目是否存在，并且为文章类型
        Navigation nav = navigationService.exist(articleQuery.getNavId());
        if (Objects.isNull(nav) || !Objects.equals(nav.getNavType(), NavTypeEnums.ARTICLE.getCode())) {
            throw new RequestParamsException("绑定的栏目不存在或绑定栏目非文章栏目");
        }
        // 获取登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        // 封装修改参数
        Article article = new Article();
        BeanUtils.copyProperties(articleQuery, article);
        article.setUpdateBy(currentUser.getUid());
        article.setUpdateTime(new Date());
        // 修改
        boolean result = updateById(article);
        if (!result) {
            return false;
        }
        // 更新栏目可用性
        navigationService.adaptUsedById(article.getNavId());
        return true;
    }

    @Override
    public boolean removeById(Long aid) {
        // 查询要删除的文章
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getAid, aid);
        wrapper.select(Article::getAid, Article::getNavId);
        Article removeArticle = getOne(wrapper);
        if (Objects.isNull(removeArticle)) {
            throw new RequestParamsException("要删除的文章不存在");
        }
        // 删除文章
        boolean result = removeById((Serializable) aid);
        if (!result) {
            return false;
        }
        // 更新栏目可用性
        navigationService.adaptUsedById(removeArticle.getNavId());
        return true;
    }

    @Override
    public FileData saveFile(MultipartFile file) {
        SaveFileVO fileVO = uploadFileService.save(file);
        FileData fileData = new FileData();
        fileData.setUrl(fileVO.getNetworkPath());
        return fileData;
    }
}




