package top.liubaiblog.masterstudio.common.exception.resource;

/**
 * @author 留白
 * @description 静态资源异常
 */
public class StaticResourceException extends RuntimeException {
    public StaticResourceException() {
    }

    public StaticResourceException(String message) {
        super(message);
    }
}
