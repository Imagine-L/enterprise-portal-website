package top.liubaiblog.masterstudiofront.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import top.liubaiblog.masterstudiofront.domain.po.Article;
import top.liubaiblog.masterstudiofront.domain.po.Navigation;
import top.liubaiblog.masterstudiofront.domain.vo.navigation.MultiNavigation;
import top.liubaiblog.masterstudiofront.domain.vo.navigation.NavPartVO;
import top.liubaiblog.masterstudiofront.enums.NavLevelEnums;
import top.liubaiblog.masterstudiofront.enums.NavTypeEnums;
import top.liubaiblog.masterstudiofront.exception.param.RequestParamsException;
import top.liubaiblog.masterstudiofront.service.ArticleService;
import top.liubaiblog.masterstudiofront.service.NavigationService;
import top.liubaiblog.masterstudiofront.mapper.NavigationMapper;
import org.springframework.stereotype.Service;
import top.liubaiblog.masterstudiofront.service.PagePlateService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 13326
 * @description 针对表【ms_navigation(栏目表)】的数据库操作Service实现
 * @createDate 2022-11-06 19:54:44
 */
@Service
public class NavigationServiceImpl extends ServiceImpl<NavigationMapper, Navigation>
        implements NavigationService {

    @Autowired
    private PagePlateService pagePlateService;

    @Autowired
    private ArticleService articleService;

    @Override
    public List<MultiNavigation> header() {
        return listByLevel(1, null);
    }

    @Override
    public List<? extends Serializable> children(String path) {
        NavPartVO navPartVO = getByPath(path);
        if (Objects.isNull(navPartVO)) {
            throw new RequestParamsException("指定路径不存在");
        }
        Long nid = navPartVO.getNid();
        Integer navType = navPartVO.getNavType();
        return children(nid, navType);
    }

    @Override
    public List<? extends Serializable> children(Long nid, Integer navType) {
        if (Objects.equals(navType, NavTypeEnums.PARENT.getCode())) {
            return getByParentId(nid);
        } else if (Objects.equals(navType, NavTypeEnums.PAGE.getCode())) {
            return pagePlateService.getByNavBinding(nid);
        } else if (Objects.equals(navType, NavTypeEnums.ARTICLE.getCode())) {
            return articleService.getByNavBinding(nid);
        } else {
            throw new RuntimeException("文章类型错误");
        }
    }

    @Override
    public NavPartVO getByPath(String path) {
        LambdaQueryWrapper<Navigation> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Navigation::getNid,
                Navigation::getNavName,
                Navigation::getLevel,
                Navigation::getImage,
                Navigation::getDescription,
                Navigation::getNavType,
                Navigation::getPath);
        wrapper.eq(Navigation::getPath, path);
        Navigation nav = getOne(wrapper);
        NavPartVO navPartVO = new NavPartVO();
        BeanUtils.copyProperties(nav, navPartVO);
        return navPartVO;
    }

    @Override
    public List<NavPartVO> getByParentId(Long nid) {
        LambdaQueryWrapper<Navigation> wrapper = new LambdaQueryWrapper<>();
        // 条件封装
        wrapper.eq(Navigation::getParentId, nid);
        wrapper.eq(Navigation::getUsed, true);
        wrapper.eq(Navigation::getDisabled, false);
        wrapper.orderByAsc(Navigation::getOrderSeed);
        // 查询
        List<Navigation> list = list(wrapper);
        // 返回值封装
        return list.stream().map(nav -> {
            NavPartVO navPartVO = new NavPartVO();
            BeanUtils.copyProperties(nav, navPartVO);
            return navPartVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<MultiNavigation> listByShowed() {
        LambdaQueryWrapper<Navigation> wrapper = new LambdaQueryWrapper<>();
        // 获取前台展示的(一级父栏目)，并且可用的、未被禁用的栏目列表
        wrapper.eq(Navigation::getLevel, NavLevelEnums.FIRST.getCode());
        wrapper.eq(Navigation::getNavType, NavTypeEnums.PARENT.getCode());
        wrapper.eq(Navigation::getShowed, true);
        wrapper.eq(Navigation::getUsed, true);
        wrapper.eq(Navigation::getDisabled, false);
        wrapper.orderByAsc(Navigation::getOrderSeed);
        // 查询所有展示的栏目
        List<Navigation> list = list(wrapper);
        return list.stream().map(nav -> {
            MultiNavigation multiNavigation = new MultiNavigation();
            BeanUtils.copyProperties(nav, multiNavigation);
            // 查询一级展示栏目中，所有文章类型的子栏目
            List<MultiNavigation> articleChildren = children(nav.getNid(), nav.getNavType())
                    .stream()
                    .filter(child -> Objects.equals(((NavPartVO) child).getNavType(), NavTypeEnums.ARTICLE.getCode()))
                    .map(child -> {
                        MultiNavigation multiChild = new MultiNavigation();
                        BeanUtils.copyProperties(child, multiChild);
                        return multiChild;
                    })
                    .collect(Collectors.toList());
            multiNavigation.setChildren(articleChildren);
            return multiNavigation;
        }).collect(Collectors.toList());
    }

    private List<MultiNavigation> listByLevel(Integer level, Long parentId) {
        NavLevelEnums levelEnums = NavLevelEnums.getInstance(level);
        LambdaQueryWrapper<Navigation> wrapper = new LambdaQueryWrapper<>();
        // 查询指定栏目，并且是可用且未被禁用的栏目列表
        wrapper.eq(Navigation::getLevel, levelEnums.getCode());
        if (!Objects.isNull(parentId)) {
            System.out.println();
            wrapper.eq(Navigation::getParentId, parentId);
        }
        wrapper.eq(Navigation::getUsed, true);
        wrapper.eq(Navigation::getDisabled, false);
        // 根据排序种子进行排序
        wrapper.orderByAsc(Navigation::getOrderSeed);
        // 查询栏目列表
        List<Navigation> list = list(wrapper);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(nav -> {
            MultiNavigation multiNavigation = new MultiNavigation();
            // 如果是父栏目，就递归获取子栏目
            if (Objects.equals(nav.getNavType(), NavTypeEnums.PARENT.getCode())) {
                BeanUtils.copyProperties(nav, multiNavigation);
                List<MultiNavigation> children = listByLevel(level + 1, nav.getNid());
                multiNavigation.setChildren(children);
            } else {
                BeanUtils.copyProperties(nav, multiNavigation);
            }
            // 如果这个栏目不可删除，说明这个栏目为通知栏目
            if (!nav.getAllowDel()) {
                multiNavigation.setNotice(true);
            }
            return multiNavigation;
        }).collect(Collectors.toList());
    }
}




