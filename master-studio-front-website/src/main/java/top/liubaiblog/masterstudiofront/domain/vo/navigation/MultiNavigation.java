package top.liubaiblog.masterstudiofront.domain.vo.navigation;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "多级栏目信息")
public class MultiNavigation implements Serializable {
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
    private Integer level;
    /**
     * 栏目类型(0父栏目, 1页面, 2文章)
     */
    @ApiModelProperty("栏目类型(0父栏目, 1页面, 2文章)")
    private Integer navType;
    /**
     * 访问路径
     */
    @ApiModelProperty("访问路径")
    private String path;
    /**
     * 是否为通知栏目
     */
    @ApiModelProperty("是否为通知栏目")
    private boolean notice;
    /**
     * 子栏目
     */
    @ApiModelProperty("子栏目")
    private List<MultiNavigation> children;

    private static final long serialVersionUID = 1L;

}
