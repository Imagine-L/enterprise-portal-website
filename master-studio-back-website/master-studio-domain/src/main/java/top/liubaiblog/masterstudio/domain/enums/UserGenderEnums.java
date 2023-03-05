package top.liubaiblog.masterstudio.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author 留白
 * @description
 */
@ToString
@Getter
@AllArgsConstructor
public enum UserGenderEnums {
    MAN("0", "男"),
    WOMAN("1", "女");
    private String code;
    private String name;

    public static UserGenderEnums getInstance(String code) {
        if ("1".equals(code)) {
            return WOMAN;
        } else {
            return MAN;
        }
    }
}
