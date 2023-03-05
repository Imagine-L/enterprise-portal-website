package top.liubaiblog.masterstudio.domain.query.profession;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "新增权限参数")
public class SaveProfessionQuery implements Serializable {
    /**
     * 岗位名称
     */
    @NotBlank(message = "[岗位名称]不能为空")
    @Size(max = 20, message = "编码长度不能超过20")
    @ApiModelProperty("岗位名称")
    @Length(max = 20, message = "编码长度不能超过20")
    private String name;
    /**
     * 岗位描述
     */
    @Size(max = 100, message = "编码长度不能超过100")
    @ApiModelProperty("岗位描述")
    @Length(max = 100, message = "编码长度不能超过100")
    private String description;
}
