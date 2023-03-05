package top.liubaiblog.masterstudio.domain.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * @author 留白
 * @description 日志模块常量类
 */
@Getter
@ToString
public enum LogModuleEnums {
    LOGIN("登录"),
    FORGET_PASSWORD("忘记密码"),
    USER("用户管理"),
    PROFESSION("岗位管理"),
    FILE("文件管理"),
    NAVIGATION("栏目管理"),
    HOME_CHART("轮播图管理"),
    PAGE_PLATE("页面管理"),
    ARTICLE("文章发布"),
    LOG("日志监控");

    private final String value;

    LogModuleEnums(String value) {
        this.value = value;
    }
}
