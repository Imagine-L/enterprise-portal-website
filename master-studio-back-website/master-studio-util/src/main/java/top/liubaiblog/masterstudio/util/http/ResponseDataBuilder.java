package top.liubaiblog.masterstudio.util.http;

import top.liubaiblog.masterstudio.domain.vo.ResponseData;
import top.liubaiblog.masterstudio.domain.enums.ResponseDataEnum;

import java.util.Objects;

/**
 * @author 留白
 * @description 构造响应结果
 */
public class ResponseDataBuilder {

    private static ResponseDataBuilder instance;

    private ResponseDataBuilder() {}

    public static ResponseDataBuilder builder() {
        if (Objects.isNull(instance)) {
            synchronized (ResponseDataBuilder.class) {
                if (Objects.isNull(instance)) {
                    instance = new ResponseDataBuilder();
                }
            }
        }
        return instance;
    }

    /**
     * 成功的请求
     */
    public <T> ResponseData<T> success(T data) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setData(data);
        responseData.setSuccess(true);
        responseData.setMessage("请求成功");
        responseData.setStatus("00000");
        responseData.setTimestamp(System.currentTimeMillis());
        return responseData;
    }

    /**
     * 失败的请求
     */
    public <T> ResponseData<T> fail(String status, String message) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setSuccess(false);
        responseData.setMessage(message);
        responseData.setStatus(status);
        responseData.setTimestamp(System.currentTimeMillis());
        return responseData;
    }

    /**
     * 根据状态码构造请求
     */
    public <T> ResponseData<T> build(ResponseDataEnum responseCode) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setSuccess(ResponseDataEnum.SUCCESS.equals(responseCode));
        responseData.setMessage(responseCode.getMessage());
        responseData.setStatus(responseCode.getStatus());
        responseData.setTimestamp(System.currentTimeMillis());
        return responseData;
    }

    /**
     * 根据状态码构造请求，并含有响应结果
     */
    public <T> ResponseData<T> build(ResponseDataEnum responseCode, T data) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setSuccess(ResponseDataEnum.SUCCESS.equals(responseCode));
        responseData.setMessage(responseCode.getMessage());
        responseData.setStatus(responseCode.getStatus());
        responseData.setData(data);
        responseData.setTimestamp(System.currentTimeMillis());
        return responseData;
    }

    /**
     * 根据ResponseData的各个参数构造ResponseData对象
     */
    public <T> ResponseData<T> build(String status, String message, T data) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setSuccess(Objects.equals(status, ResponseDataEnum.SUCCESS.getStatus()));
        responseData.setMessage(message);
        responseData.setStatus(status);
        responseData.setData(data);
        responseData.setTimestamp(System.currentTimeMillis());
        return responseData;
    }
}
