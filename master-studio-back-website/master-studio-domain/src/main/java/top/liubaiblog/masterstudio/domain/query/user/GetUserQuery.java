package top.liubaiblog.masterstudio.domain.query.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "查询用户筛选参数")
public class GetUserQuery implements Serializable {
    @ApiModelProperty("当前页码")
    @Min(value = 1, message = "查询页码不能小于1")
    private Integer pageNo;
    @ApiModelProperty("查询记录数")
    @Max(value = 100, message = "查询记录数过多")
    @Min(value = 1, message = "查询记录数过少")
    private Integer pageSize;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("用户昵称")
    private String nickname;
    @ApiModelProperty("性别")
    private String gender;
}
