package top.liubaiblog.masterstudio.domain.query.navigation;

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
@ApiModel(description = "查询栏目列表请求参数")
public class GetNavigationQuery implements Serializable {
    @ApiModelProperty("栏目名")
    private String navName;

    @ApiModelProperty("栏目级别")
    @Min(value = 1, message = "栏目级别不符合需求")
    @Max(value = 2, message = "栏目级别不符合需求")
    private Integer level;

    @ApiModelProperty("栏目类型(0父栏目, 1页面, 2文章)")
    @Min(value = 0, message = "栏目类型不符合需求")
    @Max(value = 2, message = "栏目类型不符合需求")
    private Integer navType;

    @ApiModelProperty("当前页码")
    @Min(value = 1, message = "查询页码不能小于1")
    private Integer pageNo;

    @ApiModelProperty("查询记录数")
    @Max(value = 100, message = "查询记录数过多")
    @Min(value = 1, message = "查询记录数过少")
    private Integer pageSize;
}

/*
* @NotBlank(message="[栏目名]不能为空")
    @Size(max= 10,message="编码长度不能超过10")
    @ApiModelProperty("栏目名")
    private String navName;

    @NotNull(message="[栏目级别]不能为空")
    @ApiModelProperty("栏目级别")
    @Min(value = 1, message = "栏目级别不符合需求")
    @Max(value = 2, message = "栏目级别不符合需求")
    private Integer level;

    @NotNull(message="[栏目类型(0父栏目, 1页面, 2文章)]不能为空")
    @ApiModelProperty("栏目类型(0父栏目, 1页面, 2文章)")
    @Min(value = 0, message = "栏目类型不符合需求")
    @Max(value = 2, message = "栏目类型不符合需求")
    private Integer navType;
* */
