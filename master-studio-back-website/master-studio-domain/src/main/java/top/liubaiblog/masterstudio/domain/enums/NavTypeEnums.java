package top.liubaiblog.masterstudio.domain.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * @author 留白
 * @description 文章类型枚举
 */
@Getter
@ToString
public enum NavTypeEnums {
    PARENT(0, "父栏目"),
    PAGE(1, "页面栏目"),
    ARTICLE(2, "文章栏目");

    private int code;
    private String name;

    NavTypeEnums(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static NavTypeEnums getInstance(int code) {
        switch (code) {
            case 1:
                return PAGE;
            case 2:
                return ARTICLE;
            default:
                return PARENT;
        }
    }
}
