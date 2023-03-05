package top.liubaiblog.masterstudiofront.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.liubaiblog.masterstudiofront.threadlocal.HttpThreadLocal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 留白
 * @description http请求和响应的拦截器，拦截的路径建议为"/**"，即全部拦截，这样会将请求和响应的副本存入HttpThreadLocal，
 * 通过HttpRequestUtils和HttpResponseUtils我们可以获取到这个请求和响应的副本
 */
@Slf4j
@Component
public class HttpInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("the request and response are inserted into the local thread");
        HttpThreadLocal.putRequest(request);
        HttpThreadLocal.putResponse(response);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HttpThreadLocal.remove();
    }
}
