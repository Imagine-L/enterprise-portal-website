package top.liubaiblog.masterstudio.domain.query.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "检查邮箱请求查询")
public class UpdateCheckEmailQuery implements Serializable {
    @ApiModelProperty("用户编号")
    private Long uid;
    @Email
    @Size(max = 30, message = "编码长度不能超过30")
    @ApiModelProperty("邮箱")
    @Length(max = 30, message = "编码长度不能超过30")
    private String email;
}
