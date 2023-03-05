package top.liubaiblog.masterstudiofront.threadlocal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 留白
 * @description
 */
public class HttpThreadLocal {

    private final static ThreadLocal<HttpServletRequest> LOCAL_REQUEST = new ThreadLocal<>();
    private final static ThreadLocal<HttpServletResponse> LOCAL_RESPONSE = new ThreadLocal<>();

    private HttpThreadLocal() {
    }

    public static HttpServletRequest getRequest() {
        return LOCAL_REQUEST.get();
    }

    public static HttpServletResponse getResponse() {
        return LOCAL_RESPONSE.get();
    }

    public static void putRequest(HttpServletRequest request) {
        LOCAL_REQUEST.set(request);
    }

    public static void putResponse(HttpServletResponse response) {
        LOCAL_RESPONSE.set(response);
    }

    public static void remove() {
        LOCAL_REQUEST.remove();
        LOCAL_RESPONSE.remove();
    }
}
