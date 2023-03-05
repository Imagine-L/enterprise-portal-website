package top.liubaiblog.masterstudiofront.util.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.lang.NonNull;
import top.liubaiblog.masterstudiofront.threadlocal.HttpThreadLocal;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 留白
 * @description 请求工具类
 */
public final class HttpRequestUtils {

    public final static String USER_AGENT_STRING = "User-Agent";
    public final static String AUTHORIZATION_HEADER = "Authorization";
    private final static ObjectMapper objectMapper = new ObjectMapper();

    private HttpRequestUtils() {
    }

    /**
     * 获取当前的请求，这个对象是ThreadLocal维护的，是当前线程请求的副本
     */
    public static @NonNull
    HttpServletRequest getLocalRequest() {
        HttpServletRequest request = HttpThreadLocal.getRequest();
        if (request != null) {
            return request;
        } else {
            throw new RuntimeException("Request is null, " +
                    "Please register top.liubaiblog.base.spring.interceptor.HttpInterceptor in the configuration class");
        }
    }

    /**
     * 获取当前请求的session
     */
    public static HttpSession getHttpSession() {
        return getLocalRequest().getSession();
    }

    /**
     * 设置当前请求session的参数
     */
    public static void setSessionAttribute(String key, Object value) {
        getHttpSession().setAttribute(key, value);
    }

    /**
     * 获取当前请求session的参数
     */
    public static Object getSessionAttribute(String key, Class<?> clazz) {
        Object attribute = getHttpSession().getAttribute(key);
        if (clazz.isInstance(attribute)) {
            return attribute;
        } else {
            throw new RuntimeException("Unable to get the key of the corresponding object from the session");
        }
    }

    /**
     * 解析当前线程的UserAgent头信息
     */
    public static UserAgent getUserAgent() {
        String header = getLocalRequest().getHeader(USER_AGENT_STRING);
        return new UserAgent(header);
    }

    /**
     * 获取指定cookie
     */
    public static Cookie getCookie(String key) {
        Cookie[] cookies = getLocalRequest().getCookies();
        List<Cookie> cookieList = Arrays.stream(cookies)
                .filter(filterCookie -> Objects.equals(filterCookie.getName(), key))
                .limit(1).collect(Collectors.toList());
        return cookieList.isEmpty() ? null : cookieList.get(0);
    }

    /**
     * 获取指定cookie，并执行接口的方法对cookie值进行后续处理
     */
    public static Object getCookie(String key, Function<Object, String> function) {
        Cookie cookie = getCookie(key);
        return cookie != null ? function.apply(cookie.getValue()) : null;
    }

    /**
     * 直接获取某个cookie的值，并根据类模板执行反序列化
     */
    public static Object getCookieValue(String key, Class<?> clazz) {
        try {
            Cookie cookie = getCookie(key);
            if (cookie == null) return null;
            String value = cookie.getValue();
            String valueDecode = URLDecoder.decode(value, "UTF-8");
            return objectMapper.readValue(valueDecode, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取全部cookie
     */
    public static Cookie[] getCookies() {
        return getLocalRequest().getCookies();
    }

    /**
     * 获取token
     */
    public static String getToken() {
        return getLocalRequest().getHeader(AUTHORIZATION_HEADER);
    }

    /**
     * 获取指定字段的token
     */
    public static String getToken(String header) {
        return getLocalRequest().getHeader(header);
    }

    /**
     * 获取请求头
     */
    public static String getHeader(String header) {
        return getLocalRequest().getHeader(header);
    }

    /**
     * 获取网站的基础路径
     */
    public static String getBasePath() {
        HttpServletRequest req = getLocalRequest();
        return req.getScheme()+"://" +
                req.getServerName() + ":" + req.getServerPort() +
                req.getContextPath() + '/';
    }

}
