package top.liubaiblog.masterstudio.domain.query.plate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "查询板块列表请求参数")
public class GetPlateQuery implements Serializable {
    /**
     * 板块标题
     */
    @ApiModelProperty("板块标题")
    private String plateName;
    /**
     * 板块类型(0左对齐, 1居中, 2右对齐)
     */
    @ApiModelProperty("板块类型(0左对齐, 1居中, 2右对齐)")
    private Integer plateType;
    /**
     * 绑定的栏目编号
     */
    @ApiModelProperty("绑定的栏目编号")
    private Long bindNav;

    @ApiModelProperty("当前页码")
    @Min(value = 1, message = "查询页码不能小于1")
    private Integer pageNo;

    @ApiModelProperty("查询记录数")
    @Max(value = 100, message = "查询记录数过多")
    @Min(value = 1, message = "查询记录数过少")
    private Integer pageSize;
}
