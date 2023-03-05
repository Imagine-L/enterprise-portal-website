package top.liubaiblog.masterstudio.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
public class EmailRepeatVO implements Serializable {
    @ApiModelProperty("邮箱重复的用户编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;
}
