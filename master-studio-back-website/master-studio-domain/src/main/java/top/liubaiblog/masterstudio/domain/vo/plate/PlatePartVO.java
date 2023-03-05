package top.liubaiblog.masterstudio.domain.vo.plate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "查看板块列表响应数据")
public class PlatePartVO implements Serializable {
    /**
     * 板块编号
     */
    @ApiModelProperty("板块编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;
    /**
     * 板块标题
     */
    @ApiModelProperty("板块标题")
    private String plateName;
    /**
     * 板块链接
     */
    @ApiModelProperty("板块链接")
    private String link;
    /**
     * 板块类型(0左对齐, 1居中, 2右对齐)
     */
    @ApiModelProperty("板块类型(0左对齐, 1居中, 2右对齐)")
    private String plateType;
    /**
     * 绑定的栏目编号
     */
    @ApiModelProperty("绑定的栏目名")
    private String bindNav;
    /**
     * 是否禁用
     */
    @ApiModelProperty("是否禁用")
    private Boolean disabled;
    /**
     * 是否发布
     */
    @ApiModelProperty("是否发布")
    private Boolean released;
}
