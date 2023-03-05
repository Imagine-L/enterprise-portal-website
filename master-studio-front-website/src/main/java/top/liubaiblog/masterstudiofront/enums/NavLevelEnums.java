package top.liubaiblog.masterstudiofront.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * @author 留白
 * @description 栏目等级枚举类
 */
@Getter
@ToString
public enum NavLevelEnums {
    FIRST(1, "一级栏目"),
    SECOND(2, "二级栏目");

    private int code;
    private String name;

    NavLevelEnums(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static NavLevelEnums getInstance(int code) {
        switch (code) {
            case 2:
                return SECOND;
            default:
                return FIRST;
        }
    }
}
