package top.liubaiblog.masterstudio.domain.po;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 栏目表
* @TableName ms_navigation
*/
@TableName("ms_navigation")
@Data
@ApiModel(description = "栏目表")
public class Navigation implements Serializable {

    /**
    * 栏目编号
    */
    @NotNull(message="[栏目编号]不能为空")
    @ApiModelProperty("栏目编号")
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long nid;
    /**
    * 栏目名
    */
    @NotBlank(message="[栏目名]不能为空")
    @Size(max= 10,message="编码长度不能超过10")
    @ApiModelProperty("栏目名")
    @Length(max= 10,message="编码长度不能超过10")
    private String navName;
    /**
    * 栏目级别
    */
    @NotNull(message="[栏目级别]不能为空")
    @ApiModelProperty("栏目级别")
    private Integer level;
    /**
    * 父栏目编号
    */
    @ApiModelProperty("父栏目编号")
    private Long parentId;
    /**
    * 栏目图片路径
    */
    @Size(max= 200,message="编码长度不能超过200")
    @ApiModelProperty("栏目图片路径")
    @Length(max= 200,message="编码长度不能超过200")
    private String image;
    /**
    * 栏目描述
    */
    @Size(max= -1,message="编码长度不能超过-1")
    @ApiModelProperty("栏目描述")
    @Length(max= -1,message="编码长度不能超过-1")
    private String description;
    /**
    * 是否允许删除
    */
    @NotNull(message="[是否允许删除]不能为空")
    @ApiModelProperty("是否允许删除")
    private Boolean allowDel;
    /**
    * 是否可用
    */
    @NotNull(message="[是否可用]不能为空")
    @ApiModelProperty("是否可用")
    private Boolean used;
    /**
    * 是否禁用
    */
    @NotNull(message="[是否禁用]不能为空")
    @ApiModelProperty("是否禁用")
    private Boolean disabled;
    /**
    * 是否首页展示
    */
    @NotNull(message="[是否首页展示]不能为空")
    @ApiModelProperty("是否首页展示")
    private Boolean showed;
    /**
    * 栏目类型(0父栏目, 1页面, 2文章)
    */
    @NotNull(message="[栏目类型(0父栏目, 1页面, 2文章)]不能为空")
    @ApiModelProperty("栏目类型(0父栏目, 1页面, 2文章)")
    private Integer navType;
    /**
     * 访问路径
     */
    @NotBlank(message = "访问路径不能为空")
    @ApiModelProperty("访问路径")
    @Size(min = 1, max = 10, message = "访问路径长度不合法")
    private String path;
    /**
    * 排序种子
    */
    @NotNull(message="[排序种子]不能为空")
    @ApiModelProperty("排序种子")
    private Integer orderSeed;
    /**
    * 创建者
    */
    @ApiModelProperty("创建者")
    private Long createBy;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
    * 更新者
    */
    @ApiModelProperty("更新者")
    private Long updateBy;
    /**
    * 更新时间
    */
    @ApiModelProperty("更新时间")
    private Date updateTime;
}
