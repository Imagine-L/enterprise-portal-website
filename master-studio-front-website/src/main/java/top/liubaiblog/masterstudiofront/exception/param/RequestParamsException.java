package top.liubaiblog.masterstudiofront.exception.param;

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