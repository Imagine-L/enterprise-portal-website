package top.liubaiblog.masterstudiofront.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.liubaiblog.masterstudiofront.domain.vo.ResponseData;
import top.liubaiblog.masterstudiofront.domain.vo.navigation.MultiNavigation;
import top.liubaiblog.masterstudiofront.domain.vo.navigation.NavPartVO;
import top.liubaiblog.masterstudiofront.service.NavigationService;
import top.liubaiblog.masterstudiofront.util.http.ResponseDataBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * @author 留白
 * @description
 */
@RestController
@Api(tags = "栏目控制器")
@RequestMapping("/nav")
public class NavigationController {

    @Autowired
    private NavigationService navigationService;

    @GetMapping("/header")
    @ApiOperation("获取顶部栏目")
    public ResponseData<List<MultiNavigation>> list() {
        List<MultiNavigation> list = navigationService.header();
        return ResponseDataBuilder.builder().success(list);
    }

    @GetMapping("/{path}")
    @ApiOperation("获取栏目信息")
    public ResponseData<NavPartVO> getByPath(@PathVariable("path") String path) {
        NavPartVO navPartVO = navigationService.getByPath(path);
        return ResponseDataBuilder.builder().success(navPartVO);
    }

    @GetMapping("/{path}/children")
    @ApiOperation("获取某个栏目的所有子板块")
    public ResponseData<List<? extends Serializable>> getChildren(@PathVariable("path") String path) {
        List<? extends Serializable> children = navigationService.children(path);
        return ResponseDataBuilder.builder().success(children);
    }

    @GetMapping("/list/show")
    @ApiOperation("获取首页展示的栏目列表")
    public ResponseData<List<MultiNavigation>> listByShowed() {
        List<MultiNavigation> navs = navigationService.listByShowed();
        return ResponseDataBuilder.builder().success(navs);
    }

}
