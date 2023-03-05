package top.liubaiblog.masterstudio.api.error.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import top.liubaiblog.masterstudio.common.exception.auth.AccessPermissionException;
import top.liubaiblog.masterstudio.common.exception.param.RequestParamsException;
import top.liubaiblog.masterstudio.domain.enums.ResponseDataEnum;
import top.liubaiblog.masterstudio.util.http.HttpResponseUtils;
import top.liubaiblog.masterstudio.util.http.ResponseDataBuilder;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 留白
 * @description 全局异常处理器
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 未知异常的默认处理方式
     */
    @ExceptionHandler(Exception.class)
    public void exceptionHandle(HttpServletResponse resp, Exception e) {
        printException(e);
        HttpResponseUtils.write(resp, ResponseDataBuilder
                .builder()
                .build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION, e.getCause().getMessage()));
    }

    /**
     * 访问权限异常处理
     */
    @ExceptionHandler(AccessPermissionException.class)
    public void accessPermissionExceptionHandle(HttpServletResponse resp, AccessPermissionException e) {
        printException(e);
        HttpResponseUtils.write(resp, ResponseDataBuilder
                .builder()
                .build(ResponseDataEnum.CLIENT_PERMISSION_EXCEPTION, e.getCause().getMessage()));
    }

    /**
     * 请求参数异常处理方式
     */
    @ExceptionHandler(RequestParamsException.class)
    public void requestParamsExceptionHandle(HttpServletResponse resp, RequestParamsException e) {
        printException(e);
        HttpResponseUtils.write(resp, ResponseDataBuilder
                .builder()
                .build(ResponseDataEnum.CLIENT_PARAM_EXCEPTION, e.getCause().getMessage()));
    }

    /**
     * 请求参数异常处理方式
     */
    @ExceptionHandler(BindException.class)
    public void bindExceptionHandle(HttpServletResponse resp, BindException e) {
        printException(e);
        HttpResponseUtils.write(resp,
                ResponseDataBuilder.builder().build(ResponseDataEnum.CLIENT_PARAM_EXCEPTION, e.getCause().getMessage()));
    }

    /**
     * 用户登录出现异常处理方式
     */
    @ExceptionHandler(AuthenticationException.class)
    public void authenticationExceptionHandle(HttpServletResponse resp, AuthenticationException e) {
        printException(e);
        HttpResponseUtils.write(resp,
                ResponseDataBuilder.builder().build(ResponseDataEnum.CLIENT_LOGIN_EXCEPTION, e.getCause().getMessage()));
    }


    /**
     * 打印异常日志
     */
    private void printException(Exception e) {
        log.error("================ 全局异常日志[start] ================");
        log.error("异常类: {}", e.getClass());
        log.error("错误原因: {}", e.getMessage());
        log.error("错误堆栈: ", e);
        log.error("================ 全局异常日志[end] ================");
    }

}
