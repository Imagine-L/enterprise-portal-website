package top.liubaiblog.masterstudio.domain.query.profession;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
@ApiModel(description = "查看岗位列表筛选参数")
public class GetProfessionQuery implements Serializable {
    @ApiModelProperty("当前页码")
    @Min(value = 1, message = "查询页码不能小于1")
    private Integer pageNo;

    @ApiModelProperty("查询记录数")
    @Max(value = 100, message = "查询记录数过多")
    @Min(value = 1, message = "查询记录数过少")
    private Integer pageSize;

    @ApiModelProperty("岗位编号")
    private Long pid;

    @ApiModelProperty("岗位名称")
    private String name;

    @ApiModelProperty("岗位描述")
    private String description;
}
