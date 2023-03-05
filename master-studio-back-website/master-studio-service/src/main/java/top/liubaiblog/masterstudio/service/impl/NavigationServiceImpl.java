package top.liubaiblog.masterstudio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import top.liubaiblog.masterstudio.common.exception.param.RequestParamsException;
import top.liubaiblog.masterstudio.dao.mapper.NavigationMapper;
import top.liubaiblog.masterstudio.domain.po.Article;
import top.liubaiblog.masterstudio.domain.po.Navigation;
import top.liubaiblog.masterstudio.domain.po.PagePlate;
import top.liubaiblog.masterstudio.domain.po.User;
import top.liubaiblog.masterstudio.domain.query.navigation.GetNavigationQuery;
import top.liubaiblog.masterstudio.domain.query.navigation.SaveNavigationQuery;
import top.liubaiblog.masterstudio.domain.query.navigation.UpdateNavigationQuery;
import top.liubaiblog.masterstudio.domain.enums.NavLevelEnums;
import top.liubaiblog.masterstudio.domain.enums.NavTypeEnums;
import top.liubaiblog.masterstudio.domain.vo.PageVO;
import top.liubaiblog.masterstudio.domain.vo.file.SaveFileVO;
import top.liubaiblog.masterstudio.domain.vo.navigation.MultiNavigation;
import top.liubaiblog.masterstudio.domain.vo.navigation.NavigationDetailVO;
import top.liubaiblog.masterstudio.domain.vo.navigation.NavigationPartVO;
import top.liubaiblog.masterstudio.service.*;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 13326
 * @description 针对表【ms_navigation(栏目表)】的数据库操作Service实现
 * @createDate 2022-10-03 15:23:40
 */
@Slf4j
@Service
public class NavigationServiceImpl extends ServiceImpl<NavigationMapper, Navigation>
        implements NavigationService {

    @Autowired
    private UserService userService;

    @Autowired
    private UploadFileService fileService;

    @Autowired
    @Lazy
    private PagePlateService pagePlateService;

    @Autowired
    @Lazy
    private ArticleService articleService;

    @Override
    public PageVO<NavigationPartVO> listByNavigationQuery(GetNavigationQuery navigationQuery) {
        // 条件封装
        LambdaQueryWrapper<Navigation> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Navigation::getOrderSeed);
        wrapper.orderByDesc(Navigation::getCreateTime);
        if (StringUtils.hasText(navigationQuery.getNavName())) {
            wrapper.like(Navigation::getNavName, navigationQuery.getNavName());
        }
        if (!Objects.isNull(navigationQuery.getLevel())) {
            wrapper.eq(Navigation::getLevel, navigationQuery.getLevel());
        }
        if (!Objects.isNull(navigationQuery.getNavType())) {
            wrapper.eq(Navigation::getNavType, navigationQuery.getNavType());
        }
        // 条件查询
        Page<Navigation> navigationPage = new Page<>(navigationQuery.getPageNo(), navigationQuery.getPageSize());
        page(navigationPage, wrapper);
        // 封装返回结果
        PageVO<NavigationPartVO> pageVO = new PageVO<>();
        pageVO.setTotalRecords(navigationPage.getTotal());
        pageVO.setTotalPages(navigationPage.getPages());
        List<Navigation> records = navigationPage.getRecords();
        List<NavigationPartVO> navParts = records.stream().map(nav -> {
            NavigationPartVO navPart = new NavigationPartVO();
            BeanUtils.copyProperties(nav, navPart);
            // 栏目类型
            NavTypeEnums type = NavTypeEnums.getInstance(nav.getNavType());
            navPart.setNavType(type.getName());
            // 栏目级别
            NavLevelEnums level = NavLevelEnums.getInstance(nav.getLevel());
            navPart.setLevel(level.getName());
            // 父栏目名
            if (!Objects.isNull(nav.getParentId())) {
                navPart.setParentName(getSingleNameById(nav.getParentId()));
            }
            return navPart;
        }).collect(Collectors.toList());
        pageVO.setRecords(navParts);
        return pageVO;
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
    public List<Navigation> getByParentId(Long nid) {
        LambdaQueryWrapper<Navigation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Navigation::getParentId, nid);
        wrapper.orderByDesc(Navigation::getUsed);
        return list(wrapper);
    }

    @Override
    public String getSingleNameById(Long nid) {
        LambdaQueryWrapper<Navigation> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Navigation::getNid, Navigation::getNavName);
        wrapper.eq(Navigation::getNid, nid);
        Navigation nav = getOne(wrapper);
        return Objects.isNull(nav) || !StringUtils.hasText(nav.getNavName()) ? "" : nav.getNavName();
    }

    @Override
    public NavigationDetailVO getById(Long nid) {
        // 查询信息
        Navigation nav = getById((Serializable) nid);
        // 封装返回信息
        NavigationDetailVO navDetail = new NavigationDetailVO();
        BeanUtils.copyProperties(nav, navDetail);
        // 栏目类型
        NavTypeEnums type = NavTypeEnums.getInstance(nav.getNavType());
        navDetail.setNavTypeName(type.getName());
        // 栏目级别
        NavLevelEnums level = NavLevelEnums.getInstance(nav.getLevel());
        navDetail.setLevelName(level.getName());
        // 父栏目名
        if (!Objects.isNull(nav.getParentId())) {
            navDetail.setParentName(getSingleNameById(nav.getParentId()));
        }
        // 判断是否允许展示
        navDetail.setAllowShow(allowShow(nav));
        // 创建者
        if (!Objects.isNull(nav.getCreateBy())) {
            String createBy = userService.getSingleNameById(nav.getCreateBy());
            navDetail.setCreateBy(createBy);
        }
        // 更新者
        if (!Objects.isNull(nav.getUpdateBy())) {
            String updateBy = userService.getSingleNameById(nav.getUpdateBy());
            navDetail.setUpdateBy(updateBy);
        }
        return navDetail;
    }

    @Override
    public boolean allowShow(Navigation navigation) {
        // 栏目不可用，不允许展示
        if (!navigation.getUsed()) {
            return false;
        }
        // 栏目被禁用，不允许展示
        if (navigation.getDisabled()) {
            return false;
        }
        // 不是一级栏目，或者不是父栏目，不允许展示
        if (!Objects.equals(navigation.getLevel(), NavLevelEnums.FIRST.getCode()) ||
                !Objects.equals(navigation.getNavType(), NavTypeEnums.PARENT.getCode())) {
            return false;
        }

        List<Navigation> children = getByParentId(navigation.getNid());
        // 判断子栏目是否都为文章栏目
        // long count = children.stream()
        //         .filter(child -> Objects.equals(child.getNavType(), NavTypeEnums.ARTICLE.getCode()))
        //         .count();
        // 判断是否有子栏目为文章子栏目
        boolean flag = false;
        for (Navigation child : children) {
            if (Objects.equals(child.getNavType(), NavTypeEnums.ARTICLE.getCode())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public List<NavigationPartVO> getNamesByLevel(Integer level) {
        NavLevelEnums levelEnums = NavLevelEnums.getInstance(level);
        LambdaQueryWrapper<Navigation> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Navigation::getNid, Navigation::getNavName);
        wrapper.eq(Navigation::getLevel, levelEnums.getCode());
        // 如果是一级栏目，则只查看父栏目
        if (Objects.equals(levelEnums.getCode(), NavLevelEnums.FIRST.getCode())) {
            wrapper.eq(Navigation::getNavType, NavTypeEnums.PARENT.getCode());
        }
        wrapper.orderByAsc(Navigation::getOrderSeed);
        List<Navigation> list = list(wrapper);
        return list.stream().map(navigation -> {
            NavigationPartVO navPart = new NavigationPartVO();
            BeanUtils.copyProperties(navigation, navPart);
            return navPart;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean saveByNavigationQuery(SaveNavigationQuery navigationQuery) {
        // 合法性判断
        if (Objects.equals(navigationQuery.getLevel(), NavLevelEnums.FIRST.getCode())) {
            // 如果是一级栏目，则父栏目应该为空
            if (!Objects.isNull(navigationQuery.getParentId())) {
                navigationQuery.setParentId(null);
            }
        } else {
            // 如果是二级栏目，则需要检查合法性
            secondLevelBaseCheck(navigationQuery.getNavType(), navigationQuery.getParentId());
        }
        // 获取当前登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        // 封装保存参数
        Navigation newNav = new Navigation();
        BeanUtils.copyProperties(navigationQuery, newNav);
        newNav.setUsed(false);  // 初次创建的栏目默认不可用
        newNav.setCreateBy(currentUser.getUid());
        newNav.setCreateTime(new Date());
        newNav.setUpdateBy(currentUser.getUid());
        newNav.setUpdateTime(new Date());
        // 保存文件
        SaveFileVO fileVO = fileService.save(navigationQuery.getImage());
        newNav.setImage(fileVO.getNetworkPath());
        // 存入数据库
        return save(newNav);
    }

    @Override
    public Navigation exist(Long nid) {
        LambdaQueryWrapper<Navigation> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Navigation::getNid, Navigation::getLevel, Navigation::getNavType);
        wrapper.eq(Navigation::getNid, nid);
        return getOne(wrapper);
    }

    @Override
    public String existName(String navName) {
        LambdaQueryWrapper<Navigation> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Navigation::getNid);
        wrapper.eq(Navigation::getNavName, navName);
        Navigation nav = getOne(wrapper);
        return Objects.isNull(nav) || Objects.isNull(nav.getNid()) ? "" : nav.getNid() + "";
    }

    @Override
    public String existPath(String path) {
        LambdaQueryWrapper<Navigation> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Navigation::getNid);
        wrapper.eq(Navigation::getPath, path);
        Navigation nav = getOne(wrapper);
        return Objects.isNull(nav) || Objects.isNull(nav.getNid()) ? "" : nav.getNid() + "";
    }

    @Override
    public boolean updateByNavigationQuery(UpdateNavigationQuery navigationQuery) {
        // 获取原始数据
        Navigation originNav = getById((Serializable) navigationQuery.getNid());
        if (Objects.isNull(originNav)) {
            throw new RequestParamsException("栏目编号不存在");
        }
        List<? extends Serializable> children = children(originNav.getNid(), originNav.getNavType());
        // 栏目合法性
        if (Objects.equals(navigationQuery.getLevel(), NavLevelEnums.FIRST.getCode())) {
            // 如果是一级栏目，则父栏目应该为空
            if (!Objects.isNull(navigationQuery.getParentId())) {
                navigationQuery.setParentId(null);
            }
        } else {
            // 如果是二级栏目，则需要基础的检查合法性
            secondLevelBaseCheck(navigationQuery.getNavType(), navigationQuery.getParentId());
            // 如果是不允许删除的栏目，比如通知公告，则不允许修改成二级栏目
            if (!originNav.getAllowDel()) {
                throw new RequestParamsException("无法修改为二级栏目，因为该栏目为系统提供栏目");
            }
            // 不能出现自己是自己的父栏目的情况
            if (Objects.equals(navigationQuery.getParentId(), originNav.getNid())) {
                throw new RequestParamsException("无法修改为二级栏目，自己不能做自己的父栏目");
            }
            // 如果原来是一级栏目，但是想要修改为二级栏目，则需要一级栏目下没有绑定任何子栏目、文章、页面
            if (Objects.equals(originNav.getLevel(), NavLevelEnums.FIRST.getCode())) {
                if (!children.isEmpty()) {
                    throw new RequestParamsException("无法修改为二级栏目，因为该栏目下还绑定了其他内容");
                }
            }
        }
        // 如果想要修改栏目类型，则需要原栏目下没有绑定任何子栏目、文章、页面
        if (!Objects.equals(originNav.getNavType(), navigationQuery.getNavType())) {
            if (!children.isEmpty()) {
                throw new RequestParamsException("无法修改为栏目类型，因为该栏目下还绑定了其他内容");
            }
        }
        // 是否允许展示
        if (navigationQuery.getShowed()) {
            if (!allowShow(originNav)) {
                throw new RequestParamsException("该文章不允许展示");
            }
        }
        return update(navigationQuery);
    }

    /**
     * 二级栏目基础检查
     *
     * @param navType  栏目类型
     * @param parentId 栏目的父栏目
     */
    private void secondLevelBaseCheck(Integer navType, Long parentId) {
        // 如果是二级栏目，但是栏目类型选择父栏目，则认为请求不合法
        if (Objects.equals(navType, NavTypeEnums.PARENT.getCode())) {
            throw new RequestParamsException("栏目级别和栏目类型不符合要求");
        }
        // 如果是二级栏目，一定需要有父栏目
        if (Objects.isNull(parentId)) {
            throw new RequestParamsException("父栏目不存在或选择的父栏目非一级父栏目");
        }
        // 需要判断其绑定的父栏目是否在数据库中存在，且父栏目是一级父栏目
        Navigation parentNav = exist(parentId);
        if (Objects.isNull(parentNav) ||
                !Objects.equals(parentNav.getLevel(), NavLevelEnums.FIRST.getCode()) ||
                !Objects.equals(parentNav.getNavType(), NavTypeEnums.PARENT.getCode())) {
            throw new RequestParamsException("父栏目不存在或选择的父栏目非一级父栏目");
        }
    }

    /**
     * 执行更新操作，这里的参数必须已经经过校验
     */
    private boolean update(UpdateNavigationQuery navigationQuery) {
        // 获取当前登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        // 封装保存参数
        LambdaUpdateWrapper<Navigation> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Navigation::getNid, navigationQuery.getNid());
        updateWrapper.set(Navigation::getNavName, navigationQuery.getNavName());
        updateWrapper.set(Navigation::getLevel, navigationQuery.getLevel());
        updateWrapper.set(Navigation::getParentId, navigationQuery.getParentId());
        updateWrapper.set(Navigation::getDescription, navigationQuery.getDescription());
        updateWrapper.set(Navigation::getDisabled, navigationQuery.getDisabled());
        updateWrapper.set(Navigation::getShowed, navigationQuery.getShowed());
        updateWrapper.set(Navigation::getOrderSeed, navigationQuery.getOrderSeed());
        updateWrapper.set(Navigation::getPath, navigationQuery.getPath());
        updateWrapper.set(Navigation::getNavType, navigationQuery.getNavType());
        updateWrapper.set(Navigation::getUpdateBy, currentUser.getUid());
        updateWrapper.set(Navigation::getUpdateTime, new Date());
        // 判断是否需要修改栏目图片
        MultipartFile imageFile = navigationQuery.getImage();
        if (!Objects.isNull(imageFile)) {
            // 保存文件
            SaveFileVO fileVO = fileService.save(navigationQuery.getImage());
            updateWrapper.set(Navigation::getImage, fileVO.getNetworkPath());
        }
        return update(updateWrapper);
    }

    @Override
    public boolean removeById(Long nid) {
        Navigation removeNav = getById((Serializable) nid);
        if (Objects.isNull(removeNav)) {
            throw new RequestParamsException("删除的栏目编号不存在");
        }
        // 判断该栏目是否有子栏目、页面或板块
        List<? extends Serializable> children = children(removeNav.getNid(), removeNav.getNavType());
        if (!children.isEmpty()) {
            throw new RequestParamsException("无法删除栏目，因为该栏目下还绑定了其他内容");
        }
        return removeById((Serializable) nid);
    }

    @Override
    public List<MultiNavigation> multi(Integer navType) {
        return multi(null, NavLevelEnums.FIRST.getCode(),
                navType);
    }

    @Async("threadPoolTaskExecutor")
    public void adaptUsedById(Long nid) {
        // 获取这个栏目的信息
        Navigation nav = getById((Serializable) nid);
        if (Objects.isNull(nav)) {
            return;
        }
        // 根据这个栏目的类型，获取其子模块
        List<? extends Serializable> children = children(nav.getNid(), nav.getNavType());
        // 判断子模块中是否存在可用的模块
        boolean used = false;
        if (!CollectionUtils.isEmpty(children)) {
            for (Serializable child : children) {
                if (child instanceof Navigation && ((Navigation)child).getUsed()) {
                    used = true;
                    break;
                }
                if (child instanceof PagePlate &&
                        !((PagePlate)child).getDisabled() &&
                        ((PagePlate)child).getReleased()) {
                    used = true;
                    break;
                }
                if (child instanceof Article &&
                        !((Article)child).getDisabled() &&
                        ((Article)child).getReleased()) {
                    used = true;
                    break;
                }
            }
        }
        // 更改栏目可用性
        if (!Objects.equals(nav.getUsed(), used)) {
            LambdaUpdateWrapper<Navigation> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(Navigation::getNid, nav.getNid());
            wrapper.set(Navigation::getUsed, used);
            // 如果栏目不可用，则也不允许展示
            if (!used) {
                wrapper.set(Navigation::getShowed, false);
            }
            update(wrapper);
        }
        // 递归调用上一层栏目
        if (!Objects.isNull(nav.getParentId()) &&
                !Objects.equals(nav.getLevel(), NavLevelEnums.FIRST.getCode())) {
            adaptUsedById(nav.getParentId());
        }
    }

    /**
     * 递归获取多级列表
     *
     * @param parentId 父栏目编号，一级栏目中为空
     * @param level    栏目等级
     * @param navType  栏目类型
     * @return 多级列表
     */
    private List<MultiNavigation> multi(Long parentId, Integer level, Integer navType) {
        NavTypeEnums navTypeEnums = NavTypeEnums.getInstance(navType);
        NavLevelEnums navLevelEnums = NavLevelEnums.getInstance(level);
        // 封装查询条件
        LambdaQueryWrapper<Navigation> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Navigation::getNid, Navigation::getNavName, Navigation::getLevel, Navigation::getNavType);
        wrapper.eq(Navigation::getLevel, navLevelEnums.getCode());
        if (navLevelEnums == NavLevelEnums.FIRST) {
            // 如果是一级栏目，则获取一级栏目中属于父栏目类型或属于navType类型的栏目
            wrapper.and(queryWrapper -> queryWrapper.eq(Navigation::getNavType, NavTypeEnums.PARENT.getCode())
                    .or()
                    .eq(Navigation::getNavType, navTypeEnums.getCode()));
        } else {
            // 如果是其他级别栏目，则只获取navType类型，并且父栏目为parentId的栏目
            wrapper.eq(Navigation::getNavType, navTypeEnums.getCode())
                    .eq(Navigation::getParentId, parentId);
        }
        List<Navigation> navList = list(wrapper);
        if (CollectionUtils.isEmpty(navList)) {
            return Collections.emptyList();
        }
        // 封装返回结果
        List<MultiNavigation> multiNavigations = new ArrayList<>();
        navList.forEach(nav -> {
            MultiNavigation multiNavigation = new MultiNavigation();
            if (Objects.equals(nav.getNavType(), navTypeEnums.getCode())) {
                // 如果是指定类型栏目，直接封装这个栏目对象到结果集中
                multiNavigation.setChildren(Collections.emptyList());
                BeanUtils.copyProperties(nav, multiNavigation);
                multiNavigations.add(multiNavigation);
            } else if (Objects.equals(nav.getNavType(), NavTypeEnums.PARENT.getCode())) {
                // 如果是父栏目，则获取它所有指定类型子栏目
                List<MultiNavigation> navChildren = multi(nav.getNid(),
                        navLevelEnums.getCode() + 1,
                        navTypeEnums.getCode());
                if (!CollectionUtils.isEmpty(navChildren)) {
                    multiNavigation.setChildren(navChildren);
                    BeanUtils.copyProperties(nav, multiNavigation);
                    multiNavigations.add(multiNavigation);
                }
            }
        });
        return multiNavigations;
    }
}




