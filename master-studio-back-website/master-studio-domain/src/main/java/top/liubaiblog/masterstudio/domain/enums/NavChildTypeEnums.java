package top.liubaiblog.masterstudio.domain.enums;

/**
 * @author 留白
 * @description
 */
public enum NavChildTypeEnums {
    NAV(0, "子栏目"),
    PAGE_PLATE(1, "页面板块"),
    ARTICLE(2, "文章");

    private int code;
    private String name;

    NavChildTypeEnums(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static NavChildTypeEnums getInstance(int code) {
        switch (code) {
            case 0:
                return NAV;
            case 1:
                return PAGE_PLATE;
            case 2:
                return ARTICLE;
            default:
                throw new RuntimeException("栏目子类型错误");
        }
    }
}
