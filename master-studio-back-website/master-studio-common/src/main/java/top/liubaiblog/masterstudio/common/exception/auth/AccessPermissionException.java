package top.liubaiblog.masterstudio.common.exception.auth;

/**
 * @author 留白
 * @description 访问权限异常
 */
public class AccessPermissionException extends RuntimeException {
    public AccessPermissionException() {
    }

    public AccessPermissionException(String message) {
        super(message);
    }
}
