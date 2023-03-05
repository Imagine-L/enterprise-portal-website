package top.liubaiblog.masterstudio.common.exception.auth;

import org.springframework.security.core.AuthenticationException;

/**
 * @author 留白
 * @description token非法
 */
public class TokenIllegalException extends AuthenticationException {
    public TokenIllegalException(String message) {
        super(message);
    }
}
