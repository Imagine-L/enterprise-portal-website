package top.liubaiblog.masterstudio.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "用户登录响应数据")
public class LoginVO implements Serializable {
    @ApiModelProperty("登录用户编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;
    @ApiModelProperty("登录用户名")
    private String username;
    @ApiModelProperty("登录用户昵称")
    private String nickname;
    @ApiModelProperty("是否为admin用户")
    private Boolean admin;
    @ApiModelProperty("登录用户令牌")
    private String token;
}
