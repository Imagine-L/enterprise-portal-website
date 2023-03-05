package top.liubaiblog.masterstudio.domain.vo.log;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "日志详情响应数据")
public class LogDetailVO implements Serializable {
    /**
     * 日志编号
     */
    @ApiModelProperty("日志编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long lid;
    /**
     * 操作的模块
     */
    @ApiModelProperty("操作的模块")
    private String module;
    /**
     * 操作类型
     */
    @ApiModelProperty("操作类型")
    private String operType;
    /**
     * 请求方式
     */
    @ApiModelProperty("请求方式")
    private String requestMode;
    /**
     * 请求地址
     */
    @ApiModelProperty("请求地址")
    private String requestAddr;
    /**
     * 请求json参数
     */
    @ApiModelProperty("请求json参数")
    private String requestJson;
    /**
     * 请求用户编号
     */
    @ApiModelProperty("请求用户编号")
    private String requestUser;
    /**
     * 处理的方法
     */
    @ApiModelProperty("处理的方法")
    private String handlerMethod;
    /**
     * 请求是否成功
     */
    @ApiModelProperty("请求是否成功")
    private Boolean success;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
     * 程序执行时间(ms)
     */
    @ApiModelProperty("程序执行时间(ms)")
    private Integer runtime;
    /**
     * 异常类名
     */
    @ApiModelProperty("异常类名")
    private String errorClass;
    /**
     * 异常信息
     */
    @ApiModelProperty("异常信息")
    private String errorMessage;
}
