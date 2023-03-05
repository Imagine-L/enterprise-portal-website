package top.liubaiblog.masterstudio.domain.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * @author 留白
 * @description
 */
@Getter
@ToString
public enum EmailTypeEnums {
    HTMl(0, "html"),
    TXT(1, "txt");

    private int code;
    private String name;

    EmailTypeEnums(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static EmailTypeEnums getInstance(String name) {
        if ("html".equals(name)) {
            return HTMl;
        } else {
            return TXT;
        }
    }
}
