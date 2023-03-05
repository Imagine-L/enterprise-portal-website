package top.liubaiblog.masterstudio.domain.query.forget;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "校验验证码请求参数")
public class ValidateCodeQuery implements Serializable {
    @NotBlank
    @ApiModelProperty("临时令牌")
    private String tempToken;
    @NotBlank
    @Size(max = 6, message = "编码长度不能超过6")
    @ApiModelProperty("用户输入的验证码")
    private String code;
}
