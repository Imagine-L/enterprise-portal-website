package top.liubaiblog.masterstudio.domain.query.forget;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "根据临时令牌更新密码请求参数")
public class UpdatePwdByTempTokenQuery implements Serializable {
    @NotBlank
    @ApiModelProperty("临时令牌")
    private String tempToken;
    @NotBlank
    @ApiModelProperty("新的密码")
    private String newPwd;
}
