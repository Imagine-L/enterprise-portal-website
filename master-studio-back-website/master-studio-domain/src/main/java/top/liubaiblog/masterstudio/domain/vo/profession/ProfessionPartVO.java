package top.liubaiblog.masterstudio.domain.vo.profession;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "查看岗位列表响应数据")
public class ProfessionPartVO implements Serializable {
    /**
     * 岗位编号
     */
    @ApiModelProperty("岗位编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;
    /**
     * 岗位名称
     */
    @ApiModelProperty("岗位名称")
    private String name;

    /**
     * 岗位描述
     */
    @ApiModelProperty("岗位描述")
    private String description;
    /**
     * 是否允许删除
     */
    @ApiModelProperty("是否允许删除")
    private Boolean allowDel;
}
