package top.liubaiblog.masterstudio.domain.query.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "用户修改密码参数")
public class UpdateUserPwdQuery implements Serializable {
    @NotNull
    @ApiModelProperty("用户编号")
    private Long uid;
    @NotBlank
    @ApiModelProperty("用户原密码")
    private String oldPwd;
    @NotBlank
    @ApiModelProperty("用户新密码")
    private String newPwd;
}
