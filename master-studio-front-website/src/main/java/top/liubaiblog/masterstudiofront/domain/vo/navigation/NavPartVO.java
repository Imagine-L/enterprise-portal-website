package top.liubaiblog.masterstudiofront.domain.vo.navigation;

import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel("查询栏目列表响应数据")
public class NavPartVO implements Serializable {
    /**
     * 栏目编号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("栏目编号")
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
     * 栏目图片路径
     */
    @ApiModelProperty("栏目图片路径")
    private String image;

    /**
     * 栏目描述
     */
    @ApiModelProperty("栏目描述")
    private String description;

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

    private static final long serialVersionUID = 1L;
}
