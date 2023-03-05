package top.liubaiblog.masterstudio.domain.vo.navigation;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "栏目详情响应数据")
public class NavigationDetailVO implements Serializable {
    /**
     * 栏目编号
     */
    @ApiModelProperty("栏目编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long nid;
    /**
     * 栏目名
     */
    @Size(max = 10, message = "编码长度不能超过10")
    private String navName;
    /**
     * 栏目级别
     */
    @ApiModelProperty("栏目级别")
    private Integer level;
    /**
     * 栏目级别
     */
    @ApiModelProperty("栏目级别名")
    private String levelName;
    /**
     * 父栏目编号
     */
    @ApiModelProperty("父栏目编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;
    /**
     * 父栏目名
     */
    @ApiModelProperty("父栏目名")
    private String parentName;
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
     * 是否允许删除
     */
    @ApiModelProperty("是否允许删除")
    private Boolean allowDel;
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
     * 是否首页展示
     */
    @ApiModelProperty("是否首页展示")
    private Boolean showed;
    /**
     * 是否允许首页展示(控制前台是否显示展示按钮)
     */
    @ApiModelProperty("是否允许首页展示")
    private Boolean allowShow;
    /**
     * 栏目类型(0父栏目, 1页面, 2文章)
     */
    @ApiModelProperty("栏目类型(0父栏目, 1页面, 2文章)")
    private Integer navType;
    /**
     * 栏目类型名
     */
    @ApiModelProperty("栏目类型名")
    private String navTypeName;
    /**
     * 访问路径
     */
    @ApiModelProperty("访问路径")
    private String path;
    /**
     * 排序种子
     */
    @ApiModelProperty("排序种子")
    private Integer orderSeed;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;
}
