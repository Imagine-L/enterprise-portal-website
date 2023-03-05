package top.liubaiblog.masterstudio.domain.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "登录请求参数")
public class LoginQuery implements Serializable {
    @ApiModelProperty("登录用户名")
    @Size(max = 20, message = "编码长度不能超过20")
    private String username;
    @ApiModelProperty("登录用户密码")
    @NotNull(message = "[密码]不能为空")
    private String password;
}
