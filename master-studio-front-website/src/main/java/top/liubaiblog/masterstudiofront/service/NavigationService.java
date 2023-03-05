package top.liubaiblog.masterstudiofront.service;

import top.liubaiblog.masterstudiofront.domain.po.Navigation;
import com.baomidou.mybatisplus.extension.service.IService;
import top.liubaiblog.masterstudiofront.domain.vo.navigation.MultiNavigation;
import top.liubaiblog.masterstudiofront.domain.vo.navigation.NavPartVO;
import top.liubaiblog.masterstudiofront.enums.NavLevelEnums;

import java.io.Serializable;
import java.util.List;

/**
* @author 13326
* @description 针对表【ms_navigation(栏目表)】的数据库操作Service
* @createDate 2022-11-06 19:54:44
*/
public interface NavigationService extends IService<Navigation> {

    /**
     * 获取顶部导航栏栏目
     */
    List<MultiNavigation> header();

    /**
     * 获取某个栏目的子栏目、子页面或子文章，根据该栏目的路径
     */
    List<? extends Serializable> children(String path);

    /**
     * 获取某个栏目的子栏目、子页面或子文章，根据该栏目的编号和类型
     */
    List<? extends Serializable> children(Long nid, Integer navType);

    /**
     * 根据路径获取栏目
     */
    NavPartVO getByPath(String path);

    /**
     * 根据父栏目编号获取子栏目
     */
    List<NavPartVO> getByParentId(Long nid);

    /**
     * 获取所有展示的栏目
     */
    List<MultiNavigation> listByShowed();
}
