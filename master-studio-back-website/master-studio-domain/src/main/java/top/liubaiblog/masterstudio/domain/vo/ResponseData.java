package top.liubaiblog.masterstudio.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "响应对象类型")
public class ResponseData<T> implements Serializable {
    @ApiModelProperty("请求是否成功")
    private boolean success;
    @ApiModelProperty("响应状态")
    private String status;
    @ApiModelProperty("响应消息")
    private String message;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("响应时间戳")
    private long timestamp;
    @ApiModelProperty("响应数据")
    private T data;
}
