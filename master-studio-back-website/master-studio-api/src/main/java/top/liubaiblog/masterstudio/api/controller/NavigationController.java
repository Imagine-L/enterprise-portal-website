package top.liubaiblog.masterstudio.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.liubaiblog.masterstudio.common.annotation.SetLog;
import top.liubaiblog.masterstudio.domain.enums.LogModuleEnums;
import top.liubaiblog.masterstudio.domain.query.navigation.GetNavigationQuery;
import top.liubaiblog.masterstudio.domain.query.navigation.SaveNavigationQuery;
import top.liubaiblog.masterstudio.domain.query.navigation.UpdateNavigationQuery;
import top.liubaiblog.masterstudio.domain.vo.PageVO;
import top.liubaiblog.masterstudio.domain.vo.ResponseData;
import top.liubaiblog.masterstudio.domain.enums.ResponseDataEnum;
import top.liubaiblog.masterstudio.domain.vo.navigation.MultiNavigation;
import top.liubaiblog.masterstudio.domain.vo.navigation.NavigationDetailVO;
import top.liubaiblog.masterstudio.domain.vo.navigation.NavigationPartVO;
import top.liubaiblog.masterstudio.service.NavigationService;
import top.liubaiblog.masterstudio.util.http.ResponseDataBuilder;
import top.liubaiblog.masterstudio.util.spring.SpringAppUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author 留白
 * @description
 */
@RestController
@Api(tags = "栏目管理控制器")
@RequestMapping("/nav")
public class NavigationController {

    @Autowired
    private NavigationService navigationService;

    @GetMapping("/name/{name}")
    @ApiOperation("判断栏目名是否存在，返回重复的栏目编号")
    public ResponseData<String> navNameExist(@PathVariable("name") String navName) {
        String nid = navigationService.existName(navName);
        return ResponseDataBuilder.builder().success(nid);
    }

    @GetMapping("/path/{path}")
    @ApiOperation("判断栏目路径是否存在，返回重复的栏目编号")
    public ResponseData<String> navPathExist(@PathVariable("path") String path) {
        String nid = navigationService.existPath(path);
        return ResponseDataBuilder.builder().success(nid);
    }

    @PostMapping("/list")
    @ApiOperation("获取栏目列表")
    public ResponseData<PageVO<NavigationPartVO>>
    listByNavigationQuery(@RequestBody @Validated GetNavigationQuery navigationQuery) {
        PageVO<NavigationPartVO> navs = navigationService.listByNavigationQuery(navigationQuery);
        return ResponseDataBuilder.builder().success(navs);
    }

    @GetMapping("/{nid}")
    @ApiOperation("查看栏目详情")
    public ResponseData<NavigationDetailVO> getById(@PathVariable("nid") Long nid) {
        NavigationDetailVO nav = navigationService.getById(nid);
        return ResponseDataBuilder.builder().success(nav);
    }

    @GetMapping("/level/{level}")
    @ApiOperation("获取某个等级的栏目名")
    public ResponseData<List<NavigationPartVO>> getNamesByLevel(@PathVariable("level") Integer level) {
        List<NavigationPartVO> names = navigationService.getNamesByLevel(level);
        return ResponseDataBuilder.builder().success(names);
    }

    @PostMapping
    @ApiOperation("保存栏目")
    @SetLog(module = LogModuleEnums.NAVIGATION, operType = "保存栏目")
    public ResponseData<Void> save(@Validated SaveNavigationQuery navigationQuery) {
        if (navigationService.saveByNavigationQuery(navigationQuery)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

    @PutMapping
    @ApiOperation("修改栏目")
    @SetLog(module = LogModuleEnums.NAVIGATION, operType = "修改栏目")
    public ResponseData<Void> update(UpdateNavigationQuery navigationQuery) {
        if (navigationService.updateByNavigationQuery(navigationQuery)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

    @DeleteMapping("/{nid}")
    @ApiOperation("删除栏目")
    @SetLog(module = LogModuleEnums.NAVIGATION, operType = "删除栏目")
    public ResponseData<Void> delete(@PathVariable("nid") Long nid) {
        if (navigationService.removeById(nid)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

    @GetMapping("/multi/{navType}")
    @ApiOperation("获取某个类别的多级栏目信息")
    public ResponseData<List<MultiNavigation>> multi(@PathVariable("navType") Integer navType) {
        List<MultiNavigation> navigations = navigationService.multi(navType);
        return ResponseDataBuilder.builder().success(navigations);
    }

}
