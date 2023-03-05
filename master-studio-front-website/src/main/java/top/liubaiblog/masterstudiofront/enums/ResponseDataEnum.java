package top.liubaiblog.masterstudiofront.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author 留白
 * @description
 */
@AllArgsConstructor
@Getter
@ToString
public enum ResponseDataEnum {
    /**
     * 请求成功
     */
    SUCCESS("00000", "请求成功"),
    /**
     * 客户端错误
     */
    CLIENT_EXCEPTION("A0001", "用户端端错误"),
    CLIENT_REGISTER_EXCEPTION("A0100", "用户注册错误"),
    CLIENT_LOGIN_EXCEPTION("A0200", "用户登录异常"),
    CLIENT_PERMISSION_EXCEPTION("A0300", "访问权限异常"),
    CLIENT_PARAM_EXCEPTION("A0400", "用户请求参数错误"),
    CLIENT_REQUEST_SERVER_EXCEPTION("A0500", "用户请求服务异常"),
    CLIENT_RESOURCE_EXCEPTION("A0600", "用户资源异常"),
    CLIENT_UPLOAD_FILE_EXCEPTION("A0700", "用户上传文件异常"),
    CLIENT_CURRENT_VERSION_EXCEPTION("A0800", "用户当前版本异常"),
    CLIENT_PRIVACY_UNAUTHORIZED("A0900", "用户隐私未授权"),
    CLIENT_EQUIPMENT_EXCEPTION("A1000", "用户设备异常"),
    /**
     * 服务端错误
     */
    SERVER_EXECUTE_EXCEPTION("B0001", "系统执行出错"),
    SERVER_EXECUTE_TIMEOUT("B0100", "系统执行超时"),
    SERVER_DISASTER_RECOVERY("B0200", "系统容灾功能被触发"),
    SERVER_RESOURCE_EXCEPTION("B0300", "系统资源异常"),
    /**
     * 第三方错误
     */
    THIRD_SERVICE_EXCEPTION("C0001", "调用第三方服务出错"),
    MIDDLEWARE_EXCEPTION("C0100", "中间件服务出错"),
    THIRD_SERVICE_TIMEOUT("C0200", "第三方系统执行超时"),
    DATASOURCE_EXCEPTION("C0300", "数据库服务出错"),
    THIRD_SERVICE_DISASTER_RECOVERY("C0400", "第三方容灾系统被触发"),
    NOTIFICATION_SERVICE_EXCEPTION("C0500", "通知服务出错");

    private String status;
    private String message;
}
