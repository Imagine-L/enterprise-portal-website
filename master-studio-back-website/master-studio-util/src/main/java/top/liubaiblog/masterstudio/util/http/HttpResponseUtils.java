package top.liubaiblog.masterstudio.util.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;
import top.liubaiblog.masterstudio.common.threadlocal.HttpThreadLocal;
import top.liubaiblog.masterstudio.domain.vo.ResponseData;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

/**
 * @author 留白
 * @description http响应工具类
 */
public class HttpResponseUtils {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private HttpResponseUtils() {
    }

    /**
     * 获取http响应对象
     */
    public static @NonNull
    HttpServletResponse getLocalResponse() {
        HttpServletResponse response = HttpThreadLocal.getResponse();
        if (response != null) {
            return response;
        } else {
            throw new RuntimeException("Response is null, " +
                    "Please register top.liubaiblog.base.spring.interceptor.HttpInterceptor in the configuration class");
        }
    }

    /**
     * 向响应体写入内容
     */
    public static void write(ResponseData<?> responseData) {
        try {
            String respDataString = objectMapper.writeValueAsString(responseData);
            HttpServletResponse resp = getLocalResponse();
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(respDataString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(HttpServletResponse response, ResponseData<?> responseData) {
        try {
            String respDataString = objectMapper.writeValueAsString(responseData);
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(respDataString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取响应体的输出流
     */
    public static PrintWriter getWriter() {
        try {
            return getLocalResponse().getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加cookie
     */
    public static void addCookie(Cookie cookie) {
        HttpServletResponse resp = getLocalResponse();
        resp.addCookie(cookie);
    }

    /**
     * 添加cookie，但是不需要自己传入cookie对象
     */
    public static void addCookie(String key, Object value, int secondTime) {
        try {
            String valueString = objectMapper.writeValueAsString(value);
            String valueStringEncode = URLEncoder.encode(valueString, "UTF-8");
            Cookie cookie = new Cookie(key, valueStringEncode);
            cookie.setMaxAge(secondTime);
            addCookie(cookie);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
