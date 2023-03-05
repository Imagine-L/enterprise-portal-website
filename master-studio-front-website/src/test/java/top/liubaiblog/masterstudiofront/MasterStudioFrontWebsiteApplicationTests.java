package top.liubaiblog.masterstudiofront;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.liubaiblog.masterstudiofront.domain.po.Article;
import top.liubaiblog.masterstudiofront.domain.vo.article.ArticlePartVO;
import top.liubaiblog.masterstudiofront.domain.vo.navigation.MultiNavigation;
import top.liubaiblog.masterstudiofront.domain.vo.navigation.NavPartVO;
import top.liubaiblog.masterstudiofront.enums.NavTypeEnums;
import top.liubaiblog.masterstudiofront.service.NavigationService;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootTest
class MasterStudioFrontWebsiteApplicationTests {

    @Autowired
    private NavigationService navigationService;

    @Test
    void contextLoads() {
//        List<MultiNavigation> multiNavigations = navigationService.header();
//        multiNavigations.forEach(System.out::println);
        List<MultiNavigation> navPartVOS = navigationService.listByShowed();

//        navPartVOS.forEach((nav) -> {
//            List<? extends Serializable> children = navigationService.children(nav.getNid(), nav.getNavType());
//            List<MultiNavigation> articleChildren = children.stream()
//                    .filter(child -> Objects.equals(((NavPartVO) child).getNavType(), NavTypeEnums.ARTICLE.getCode()))
//                    .map(child -> {
//                        MultiNavigation multiNavigation = new MultiNavigation();
//                        BeanUtils.copyProperties(child, multiNavigation);
//                        return multiNavigation;
//                    })
//                    .collect(Collectors.toList());
//            nav.setChildren(articleChildren);
//        });
        navPartVOS.forEach(System.out::println);
    }

}
