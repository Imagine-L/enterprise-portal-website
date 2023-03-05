package top.liubaiblog.masterstudio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.liubaiblog.masterstudio.domain.po.Navigation;
import top.liubaiblog.masterstudio.domain.query.navigation.GetNavigationQuery;
import top.liubaiblog.masterstudio.domain.query.navigation.SaveNavigationQuery;
import top.liubaiblog.masterstudio.domain.query.navigation.UpdateNavigationQuery;
import top.liubaiblog.masterstudio.domain.vo.PageVO;
import top.liubaiblog.masterstudio.domain.vo.navigation.MultiNavigation;
import top.liubaiblog.masterstudio.domain.vo.navigation.NavigationDetailVO;
import top.liubaiblog.masterstudio.domain.vo.navigation.NavigationPartVO;

import java.io.Serializable;
import java.util.List;

/**
* @author 13326
* @description 针对表【ms_navigation(栏目表)】的数据库操作Service
* @createDate 2022-10-03 15:23:40
*/
public interface NavigationService extends IService<Navigation> {

    /**
     * 根据查询条件获取栏目列表
     */
    PageVO<NavigationPartVO> listByNavigationQuery(GetNavigationQuery navigationQuery);

    /**
     * 获取某个栏目的子栏目、子页面或子文章
     */
    List<? extends Serializable> children(Long nid, Integer navType);

    /**
     * 根据父栏目编号获取子栏目
     */
    List<Navigation> getByParentId(Long nid);

    /**
     * 获取栏目的父栏目名
     */
    String getSingleNameById(Long nid);

    /**
     * 根据编号获取栏目详细信息
     */
    NavigationDetailVO getById(Long nid);

    /**
     * 判断某个栏目是否允许展示
     * @param navigation 从数据库中查询出的栏目，栏目的各个属性必须是真实有效的
     */
    boolean allowShow(Navigation navigation);

    /**
     * 获取某个等级的所有栏目名
     */
    List<NavigationPartVO> getNamesByLevel(Integer level);

    /**
     * 根据条件保存栏目
     */
    boolean saveByNavigationQuery(SaveNavigationQuery navigationQuery);

    /**
     * 判断指定等级栏目是否存在
     */
    Navigation exist(Long nid);

    /**
     * 判断指定栏目名是否存在，返回重复的栏目编号
     */
    String existName(String navName);

    /**
     * 判断指定栏目路径是否存在，返回重复的栏目编号
     */
    String existPath(String path);

    /**
     * 根据条件修改栏目
     */
    boolean updateByNavigationQuery(UpdateNavigationQuery navigationQuery);

    /**
     * 删除栏目
     */
    boolean removeById(Long nid);

    /**
     * 获取某个类型的多级栏目列表
     */
    List<MultiNavigation> multi(Integer navType);

    /**
     * 根据编号，自动适应该栏目链上(由底向上)的栏目可用性
     * 栏目是否可用符合以下原则：
     * - 父栏目，只要有子栏目可用，则父栏目也可用
     * - 页面栏目，只要绑定的板块列表中有未禁止且发布的，则该页面可用
     * - 文章栏目，只要绑定的文章列表中有未禁止且发布的，则该文章可用
     */
    void adaptUsedById(Long nid);
}
