package top.liubaiblog.masterstudio.domain.po;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 日志表
* @TableName ms_log
*/
@TableName("ms_log")
@Data
@ApiModel(description = "日志表")
public class Log implements Serializable {

    /**
    * 日志编号
    */
    @NotNull(message="[日志编号]不能为空")
    @ApiModelProperty("日志编号")
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long lid;
    /**
    * 操作的模块
    */
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("操作的模块")
    @Length(max= 20,message="编码长度不能超过20")
    private String module;
    /**
     * 操作类型
     */
    @ApiModelProperty("操作类型")
    private String operType;
    /**
    * 请求方式
    */
    @Size(max= 6,message="编码长度不能超过6")
    @ApiModelProperty("请求方式")
    @Length(max= 6,message="编码长度不能超过6")
    private String requestMode;
    /**
    * 请求地址
    */
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("请求地址")
    @Length(max= 100,message="编码长度不能超过100")
    private String requestAddr;
    /**
    * 请求json参数
    */
    @Size(max= -1,message="编码长度不能超过-1")
    @ApiModelProperty("请求json参数")
    @Length(max= -1,message="编码长度不能超过-1")
    private String requestJson;
    /**
    * 请求用户编号
    */
    @ApiModelProperty("请求用户编号")
    private Long requestUser;
    /**
    * 处理的方法
    */
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("处理的方法")
    @Length(max= 100,message="编码长度不能超过100")
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
