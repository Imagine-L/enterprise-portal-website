package top.liubaiblog.masterstudio.domain.vo.wangeditor;

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
@ApiModel(description = "wangEditor富文本框架需要的响应报文")
public class EditorResponse <T> implements Serializable {
    @ApiModelProperty("错误码")
    private Integer errno;
    @ApiModelProperty("响应信息")
    private String message;
    @ApiModelProperty("响应数据")
    private T data;

    public static EditorResponse<Object> ok(Object data) {
        return new EditorResponse<>(0, null, data);
    }

    public static EditorResponse<Object> error(Integer errno, String message) {
        return new EditorResponse<>(errno, message, null);
    }
}
