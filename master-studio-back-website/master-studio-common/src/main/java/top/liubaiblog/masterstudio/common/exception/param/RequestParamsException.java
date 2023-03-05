package top.liubaiblog.masterstudio.common.exception.param;

import java.net.BindException;

/**
 * @author 留白
 * @description 请求参数异常
 */
public class RequestParamsException extends RuntimeException {
    public RequestParamsException(String msg) {
        super(msg);
    }

    public RequestParamsException() {
    }
}
