package top.liubaiblog.masterstudio.domain.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * @author 留白
 * @description
 */
@Getter
@ToString
public enum PlateTypeEnums {
    LEFT(0, "左对齐"),
    MIDDLE(1, "居中对齐"),
    RIGHT(2, "右对齐");

    private int code;
    private String name;

    PlateTypeEnums(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static PlateTypeEnums getInstance(int code) {
        switch (code) {
            case 1:
                return MIDDLE;
            case 2:
                return RIGHT;
            default:
                return LEFT;
        }
    }
}
