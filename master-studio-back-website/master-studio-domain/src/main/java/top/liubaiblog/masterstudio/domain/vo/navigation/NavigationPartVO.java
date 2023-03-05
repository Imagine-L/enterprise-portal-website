package top.liubaiblog.masterstudio.domain.vo.navigation;

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
@ApiModel(description = "查看栏目列表响应数据")
@Data
public class NavigationPartVO implements Serializable {
    /**
     * 栏目编号
     */
    @ApiModelProperty("栏目编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long nid;
    /**
     * 栏目名
     */
    @ApiModelProperty("栏目名")
    private String navName;
    /**
     * 栏目级别
     */
    @ApiModelProperty("栏目级别")
    private String level;
    /**
     * 父栏目编号
     */
    @ApiModelProperty("父栏目名")
    private String parentName;
    /**
     * 是否可用
     */
    @ApiModelProperty("是否可用")
    private Boolean used;
    /**
     * 是否禁用
     */
    @ApiModelProperty("是否禁用")
    private Boolean disabled;
    /**
     * 栏目类型(0父栏目, 1页面, 2文章)
     */
    @ApiModelProperty("栏目类型(0父栏目, 1页面, 2文章)")
    private String navType;
    /**
     * 访问路径
     */
    @ApiModelProperty("访问路径")
    private String path;
    /**
     * 是否允许删除
     */
    @ApiModelProperty("是否允许删除")
    private Boolean allowDel;
}
