package top.liubaiblog.masterstudio.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.liubaiblog.masterstudio.domain.vo.navigation.MultiNavigation;
import top.liubaiblog.masterstudio.service.NavigationService;
import top.liubaiblog.masterstudio.service.impl.NavigationServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 留白
 * @description
 */
@Slf4j
@SpringBootTest
public class MapperTest {

    @Autowired
    private NavigationService navigationService;

    @Test
    public void test() {
        log.info("当前线程：{}", Thread.currentThread().getName());
        navigationService.adaptUsedById(1586694540198256641L);
    }
}
