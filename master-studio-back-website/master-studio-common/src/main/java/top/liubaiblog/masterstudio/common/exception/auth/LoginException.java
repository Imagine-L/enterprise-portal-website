package top.liubaiblog.masterstudio.common.exception.auth;

import org.springframework.security.core.AuthenticationException;

/**
 * @author 留白
 * @description 登录异常
 */
public class LoginException extends AuthenticationException {
    public LoginException(String message) {
        super(message);
    }
}
